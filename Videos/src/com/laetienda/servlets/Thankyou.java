package com.laetienda.servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.laetienda.utilities.DB;
import com.laetienda.utilities.Logger;

public class Thankyou extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger log;
	private DB db;
	private String[] pathParts;
	private String thankyou;
       
    public Thankyou() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		db = (DB)config.getServletContext().getAttribute("db");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		buildServlet(request, response);
		
		// /thankyou/*
		if(pathParts.length == 3 && pathParts[2].equals(thankyou)){
			
			request.getSession().removeAttribute("thankyou");
			request.getRequestDispatcher(db.getSetting("jsp_folder") + "thankyou/" + pathParts[2]).forward(request, response);
		}
		else{
			response.getWriter().append("Served at: ").append(request.getContextPath()).append("\n")
								.append(request.getRequestURI()).append("\n");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		buildServlet(request, response);
		doGet(request, response);
	}
	
	private void buildServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession(); 
		
		log =(Logger)session.getAttribute("Logger");
		log.info("Executing App servlet");
		pathParts = (String[])request.getAttribute("pathParts");	
		thankyou = (String)request.getSession().getAttribute("thankyou");
	}
}
