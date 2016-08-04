package com.laetienda.listeners;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.laetienda.utilities.DB;
import com.laetienda.utilities.Logger;

public class LoadApp implements ServletContextListener {
	
	private DB db;
	private Logger log;

    public LoadApp() {
        // TODO Auto-generated constructor stub
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    	log.info("Destroying context");
    	db.closeEm(log.getEm());
    	db.close();
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
    	ServletContext sc = arg0.getServletContext();
    	
    	intializeDB(sc);
    	initializeLogger(sc);
    }
    
    private void intializeDB(ServletContext sc){
    	
    	System.out.println("Initializing database");
    	
    	String appName = (String)sc.getInitParameter("appName");
    	
    	try{
    		db = new DB(appName);
    		sc.setAttribute("db", db);
    		System.out.println("The application \"" + appName + "\" has been succesfully conected to the database");
        
    	}catch (IOException ex){
    		System.out.println("Can not create connection to the database because entity manager has been closed.");
    		ex.printStackTrace();
    		System.exit(1);
    	}
    }
    
    private void initializeLogger(ServletContext sc){
    	
    	Logger.setDevMode();
    	this.log = new Logger(db.getUserByUsername("Logger"), db.getEm());
    	log.info("Logger has been initialized");
    	log.info("The application has initialized succesfully");
    }
}
