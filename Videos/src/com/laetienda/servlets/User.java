package com.laetienda.servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;

import com.laetienda.utilities.Auth;
import com.laetienda.utilities.DB;
import com.laetienda.utilities.DbTransaction;
import com.laetienda.utilities.Mailer;
import com.laetienda.utilities.Logger;
import com.laetienda.beans.Page;

public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DB db;
	private Mailer mailer;
	private Logger log;
	private String[] pathParts;
	private Page page;
       
    public User() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		db = (DB)config.getServletContext().getAttribute("db");
		mailer = (Mailer)config.getServletContext().getAttribute("mailer");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		buildServlet(request, response);
		log.info("Executing User servlet->doGet");
		
		// /user/password/confirm
		if(pathParts.length == 4 && pathParts[2].equals("password") && pathParts[3].equals("confirm")){
			doGet_pwdConfirm(request, response);
			
		// /user/password
		}else if(pathParts.length == 3 && pathParts[2].equals("password")){
			request.getRequestDispatcher(db.getSetting("jsp_folder") + "user/pwdChange.jsp").forward(request, response);
			
		// /user/all 
		}else if(pathParts.length == 3 && pathParts[2].equals("all")){
			request.getRequestDispatcher(db.getSetting("jsp_folder") + "user/all.jsp").forward(request, response);
		}
		else{
			response.getWriter().append("Served at: ").append(request.getContextPath()).append("\n")
			.append(request.getRequestURI()).append("\n");
		}
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		buildServlet(request, response);
		
		String submit =  request.getParameter("submit");
		
		if(submit.equals("pwdConfirm")){
			doPost_pwdConfirm(request, response);
		}else if(submit.equals("pwdChange")){
			pwdChange(request, response);
		}
		else{
			log.notice("Bad form submission. $submit: " + submit);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void buildServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		
		log = (Logger)session.getAttribute("Logger");
		pathParts = (String[])request.getAttribute("pathParts");
		page = (Page)request.getAttribute("page");
	}
	
	private void doGet_pwdConfirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		EntityManager em = db.getEm();
		try{
			
			String username = page.decode(request.getParameter("id"));
			com.laetienda.entities.User usuario = em.createNamedQuery("User.findByUsername", com.laetienda.entities.User.class).setParameter("username", username).getSingleResult();
			request.setAttribute("email", usuario.getUsername());
			request.getRequestDispatcher(db.getSetting("jsp_folder") + "user/pwdConfirm.jsp").forward(request, response);
			
		}catch (Exception ex){
			log.exception(ex);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}finally{
			db.closeEm(em);
		}
	}
	
	private void doPost_pwdConfirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("executing doPost_pwdConfim method of user servlet");
		
		try{
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("auth");
		}catch (Exception ex){
			log.exception(ex);
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		DbTransaction tran = new DbTransaction(db.getEm(), log);
				
		try{
			
			String username2 = page.decode(request.getParameter("id"));
			com.laetienda.entities.User usuario = tran.getEm().createNamedQuery("User.findByUsername", com.laetienda.entities.User.class).setParameter("username", username).getSingleResult();
		
			if(username2.equals(username)){
						
				if(usuario.validatePassword(password)){
					usuario.setStatus("valid");
				}else{
					usuario.addError("password", "login_password_not_match");
				}
				
			}else{
				usuario.addError("username", "username_error");
			}
			
			if(usuario.getErrors().size() <= 0){
				
				if(tran.commit()){
					log.debug("User has been confirmed credentials succesfully");
				}else{
					usuario.addError("user", "user_error_internal");
				}
			}
			
			if(usuario.getErrors().size() > 0){
				request.setAttribute("user", usuario);
				doGet(request, response);
			}else{
				request.getSession().setAttribute("thankyou", "pwdConfirm");
				response.sendRedirect(page.getUrl() + "/thankyou/pwdConfirm");
			}
			
		}catch(Exception ex){
			log.exception(ex);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}finally{
			db.closeEm(tran.getEm());
		}
	}
	
	private void pwdChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Changing user password");
		
		String old_password = request.getParameter("old_password");
		String new_password = request.getParameter("new_password");
		String new_passw_confirm = request.getParameter("new_passw_confirm");
		com.laetienda.entities.User user = (com.laetienda.entities.User)request.getSession().getAttribute("user");
		DbTransaction tran = new DbTransaction(db.getEm(), log);
		
		try{
			com.laetienda.entities.User usuario = tran.getEm().find(com.laetienda.entities.User.class, user.getId());
			usuario.setNewPassword(old_password, new_password, new_passw_confirm);
			
			if(usuario.getErrors().size() <= 0){
				if(tran.commit()){
					log.info("password has been updated succesfully");
				}else{
					usuario.addError("user", "user_error_internal");
				}
			}
			
			if(usuario.getErrors().size() > 0){
				request.setAttribute("user", usuario);
				doGet(request, response);
			}else{
				request.getSession().setAttribute("thankyou", "pwdChange");
				response.sendRedirect(page.getUrl() + "/thankyou/pwdChange");
			}
			
		}catch(Exception ex){
			log.exception(ex);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}finally{
			db.closeEm(tran.getEm());
		}
	}
}
