package com.laetienda.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import com.laetienda.entities.User;


public class Auth {
	
	private User user;
	private String username;
	private HashMap<String, List<String>> errors;
	private EntityManager em;
	private Logger log;
	
	public Auth(Logger log, EntityManager em, String username, String password){
		
		this.em = em;
		this.log = log;
		this.username = username;
		log.info("Validationg user. $username: " + username);
		
		try{
			user = getEm().createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();
		}catch(Exception ex){
			addError("username", "login_user_not_found");
			log.debug("User not found in database");
		}
		
		if(user != null){
			
			if(!user.validatePassword(password)){
				addError("password", "login_password_not_match");
			}
			
			if(user.isValid() != null && !user.isValid().isEmpty()){
				addError("login", user.isValid());				
			}
		}
	}
	
	public EntityManager getEm(){
		return this.em;
	}
	
	private void addError(String name, String error){
		
		if(getErrors() == null){
			this.errors = new HashMap<String, List<String>>();
		}
		
		List<String> errorList = getErrors().get(name);
		
		if(errorList == null){
			errorList = new ArrayList<String>();
			getErrors().put(name, errorList);
		}
		
		errorList.add(error);
	}
	
	public HashMap<String, List<String>> getErrors(){
		if(errors == null){
			errors = new HashMap<String, List<String>>(); 
		}
		
		return errors;
	}
	
	public String getUsername(){
		return username;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public String getTest(){
		return "auth test";
	}
}
