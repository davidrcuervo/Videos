package com.laetienda.utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import com.laetienda.utilities.Logger;
import com.laetienda.entities.User;
import com.laetienda.entities.Setting;

public class DB {
	
	private EntityManagerFactory emfactory;
    private Logger log;
    private List<EntityManager> ems;
    private HashMap<String, User> userByUsername;
    private HashMap<String, String> settings;
    
    public DB(String persistenceUnitName) throws IOException{
    	
    	userByUsername = new HashMap<String, User>();
    	settings = new HashMap<String, String>();
    	ems = new ArrayList<EntityManager>();
    	createEmFactory(persistenceUnitName);
    	setDatabaseLogger();
    }
    
    private void createEmFactory(String persistenceUnitName) throws IOException{
    	System.out.println("Creating entity manager. $persistenceUnitName: " + persistenceUnitName);
    	
    	try{
    		emfactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    	}catch (IllegalStateException ex){
    		
    		throw new IOException(ex);
    	}catch(PersistenceException ex){
    		
    		throw new IOException(ex);
    	}
    }
    
    private void setDatabaseLogger(){
    	
    	User user = getUserByUsername("Logger");
    	log = new Logger(user, getEm());
    	log.setUser(user);
    }
    
    public EntityManager getEm() throws IllegalStateException {
    	
		EntityManager em; 
		em = emfactory.createEntityManager();
		ems.add(em);
		return em;
    }
    
    public boolean closeEm(EntityManager em){
    	
    	boolean result = false;
    	
    	if(ems.contains(em)){
    		int index = ems.indexOf(em);
    		EntityManager temp = ems.get(index);
    		if(temp.isOpen()){
    			temp.clear();
    			temp.close();
    		}
    		ems.remove(index);
    		result = true;
    	}
    	
    	return result;
    }
    
    public void close(){
    	log.info("Closing database connection");
    	
    	this.closeEm(log.getEm());
    	
    	for(int c = 0; c < ems.size(); c++){
    		if(ems.get(c).isOpen()){
    			ems.get(c).clear();
    			ems.get(c).close();
    		}
    		ems.remove(c);
    	}
    	
    	emfactory.close();
    }
    
    public User getUserByUsername(String username){
   	    	
    	User result = userByUsername.get(username);
    	
    	if(result == null){
    		try{
    			EntityManager em = getEm();
    			result = em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();
        		userByUsername.put(result.getUsername(), result);
        		
        		closeEm(em);
    		}catch(Exception ex){
    			ex.printStackTrace();
    			log.notice("User does not exist yet. $username: " + username);
    			log.exception(ex);
    		}
    	}

    	return result;
    }
    
    public String getSetting(String setting){
    	
    	String result = settings.get(setting);
    	
    	if(result == null){
    		try{
    			EntityManager em = getEm();
        		Setting temp = em.createNamedQuery("Setting.findByVariable", Setting.class).setParameter("variable", setting).getSingleResult();
        		result = temp.getSetting().getValue();
        		settings.put(setting, result);
        		closeEm(em);
    		}catch(Exception ex){
    			log.notice("Setting does not exist. $setting: " + setting);
    			log.exception(ex);
    		}
    	}
    	return result;
    }
}
