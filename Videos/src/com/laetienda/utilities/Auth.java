package com.laetienda.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import com.laetienda.entities.User;


public class Auth {
	
	private User user;
	private HashMap<String, List<String>> errors;
	private EntityManager em;
	private Logger log;
	
	public Auth(Logger log, EntityManager em, String username, String password){
		
		this.em = em;
		this.log = log;
		log.info("Validationg user. $username: " + username);
		
		try{
			user = getEm().createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();
		}catch(Exception ex){
			addError("login", "login_user_not_found");
			log.debug("User not found in database");
		}
		
		if(user != null){
			if(user.getPassword().equals(password)){
				log.debug("user has been validated correctly. $username: " + username);
			}else{
				addError("login", "login_password_not_match");
			}
		}
		
	}
	
	public EntityManager getEm(){
		return this.em;
	}
	
	private void addError(String name, String error){
		
		if(errors.get(name) == null){
			List<String> temp = new ArrayList<String>();
			errors.put(name, temp);
		}
		
		List<String> temp2 = errors.get(name);
		temp2.add(error);
	}
	
	public HashMap<String, List<String>> getErrors(){
		return this.errors;
	}
	
	public User getUser(){
		return this.user;
	}
}
