package com.laetienda.servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.laetienda.entities.User;
import com.laetienda.utilities.Logger;
import com.laetienda.utilities.DB;
import com.laetienda.utilities.Auth;
import com.laetienda.utilities.DbTransaction;
import com.laetienda.utilities.Mailer;
import com.laetienda.beans.Page;

public class App extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger log;
	private DB db;
	private String[] pathParts;
	private User user;
	private Page page;
	private Mailer mailer;
       
    public App() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		
		db = (DB)config.getServletContext().getAttribute("db");
		mailer = (Mailer)config.getServletContext().getAttribute("mailer");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		buildServlet(request, response);
		
		// /
		if(!(pathParts.length > 0)){
			request.getRequestDispatcher(db.getSetting("jsp_folder") + "home.jsp").forward(request, response);
		
		// /login
		}if(pathParts.length == 2 && pathParts[1].equals("login")){
			request.getRequestDispatcher(db.getSetting("jsp_folder") + "login.jsp").forward(request, response);
		}
		else{
			response.getWriter().append("Served at: ").append(request.getContextPath()).append("\n")
								.append(request.getRequestURI()).append("\n");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		buildServlet(request, response);
		
		String submit = request.getParameter("submit");
		
		if(submit.equals("login")){
			validateUser(request, response);
		
		}else if(submit.equals("signup")){
			signup(request, response);
		}
		else{
			log.notice("Bad form submission. $submit: " + submit);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void buildServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession(); 
		
		log =(Logger)session.getAttribute("Logger");
		log.info("Executing App servlet");
		
		pathParts = (String[])request.getAttribute("pathParts");
		user = (User)session.getAttribute("user");
		page = (Page)request.getAttribute("page");
		
	}
	
	private void validateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.info("validating user");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Auth auth = new Auth(log, db.getEm(), username, password);
		db.closeEm(auth.getEm());
		
		if(auth.getErrors().size() > 0){
			request.setAttribute("auth", auth);
		}else{
			user = auth.getUser();
			log.setUser(user);
			
			com.laetienda.utilities.Lang lang = (com.laetienda.utilities.Lang)request.getSession().getAttribute("lang");
			lang.setUser(user);
			request.getSession().setAttribute("auth", auth);
			request.getSession().setAttribute("user", user);
			response.sendRedirect((String)request.getSession().getAttribute("current_uri"));
		}
	}

	private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		log.info("signing up a new user");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password_confirm = request.getParameter("password_confirm");
		
		DbTransaction tran = new DbTransaction(db.getEm(), log);
		
		User user = new User();
		user.setLogger(log);
		user.setUsername(email);
		user.setPassword(password, password_confirm);
		
		try{
			com.laetienda.entities.App app = tran.getEm().createNamedQuery("App.findByName", com.laetienda.entities.App.class).setParameter("name", "Videos").getSingleResult();
			app.setLogger(log);
			user.setMail(mailer.createMail(user, log));
			app.addUser(user);
			user.setStatus("registered");
			
			if(user.getErrors().size() <= 0){
				if(!tran.commit()){
					user.addError("signup", "user_error_internal");
				}
			}

		}catch (Exception ex){
			user.addError("user", "user_error_internal");
			log.critical("Application not found");
			log.exception(ex);
		}finally{
			db.closeEm(tran.getEm());
			
			if(user.getErrors().size() > 0){
				request.setAttribute("user", user);
				doGet(request, response);
			}else{
				request.getSession().setAttribute("thankyou", "signup");
				response.sendRedirect(page.getUrl() + "thankYou/signup");
			}
		}
	}
}
