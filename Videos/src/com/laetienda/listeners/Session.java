package com.laetienda.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSession;

import com.laetienda.utilities.DB;
import com.laetienda.utilities.Logger;
//import com.laetienda.entities.User;
import com.laetienda.utilities.Lang;

public class Session implements HttpSessionListener {
	
	//private User user;
	private Logger log;
	private DB db;
	private Lang lang;
	private HttpSession session;

    public Session() {
        // TODO Auto-generated constructor stub
    }

    public void sessionCreated(HttpSessionEvent arg0){
    	
    }

    public void sessionDestroyed(HttpSessionEvent arg0)  {
    	
    	session = arg0.getSession();
    	log = (Logger)session.getAttribute("Logger");
    	db = (DB)session.getServletContext().getAttribute("db");
    	log.info("Closing session");
    	
    	//user = (User)arg0.getSession().getAttribute("user");
       	lang = (Lang)arg0.getSession().getAttribute("lang");
       	
    	db.closeEm(lang.getEm());
    	db.closeEm(log.getEm());
    }
}
