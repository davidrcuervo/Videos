package com.laetienda.utilities;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.persistence.EntityManager;
import com.laetienda.entities.Variable;
import com.laetienda.utilities.Logger;
import com.laetienda.entities.Language;
import com.laetienda.entities.User;
import com.laetienda.entities.Value;

/**
 * @author myself
 *
 */
public class Lang {
	
	private HashMap<String, Language> langs;
	private EntityManager em;
	private User user;
	private Logger log;
	private String headerLanguage;
	private Cookie cookie;
	
	/**
	 * @param em	entity manager that will be encharged to pull data from the database.
	 * @param user	This user will be used to get the language at the moment to return a phrase
	 * @param log
	 */
	public Lang(EntityManager em, User user, Logger log){
		
		this.log = log;
		this.em = em;
		this.user = user;
				
		langs = new HashMap<String, Language>();
	}
	
	/**
	 * @return	The entity manager from the constructor. It is used to close the connection when the servlet finishes.
	 */
	public EntityManager getEm(){
		return this.em;
	}
	
	/**
	 * @return Return the lang of the user.<br />
	 * If the user has no lang configured it will return the templang<br /> 
	 * templang comes from a cookie or from the settings of the web expleror
	 * default lang is english if it is different from spanish, english or french 
	 */
	public String getLang(){
		
		String result; 
		
		if(!user.getLanguage().getValue().equals("none")){
			result = user.getLanguage().getValue();
		}else if(getCookie().getValue() != null && !getCookie().getValue().isEmpty()){
			result = getCookie().getValue();
		}else if(getHeaderLanguage() != null && !getHeaderLanguage().isEmpty()){
			result = getHeaderLanguage();
		}else{
			result = "en";
		}
		
		return result;
	}
	
	/**
	 * @param user
	 */
	public void setUser(User user){
		this.user = user;
	}
	
	
	public void setCookie(Cookie cookie){
		
		if(cookie == null){
			this.cookie = new Cookie("lang", null);
		}else{
			this.cookie = cookie;
		}
	}
	
	public Cookie getCookie(){
		return cookie;
	}
	
	public void setHeaderLanguage(String headerLanguage){
		
		if(headerLanguage == null || headerLanguage.isEmpty()){
			
		}else{
			//TODO find the langugage based on the header
			this.headerLanguage = headerLanguage;
		}
	}
	
	private String getHeaderLanguage(){
		return headerLanguage;
	}
	
	/**
	 * @param identifier
	 * @return
	 */
	public String out(String identifier){
		
		String result;
		Language lang = langs.get(identifier);
		
		if(lang == null){
			try{
				lang = em.createNamedQuery("Language.findByIdentifier", Language.class).setParameter("identifier", identifier).getSingleResult();
				langs.put(identifier, lang);
				
			}catch(Exception ex){
				log.error("The requested text does not exist in the language table. $identifier: " + identifier);
				log.exception(ex);
			}finally{
				em.clear();
			}
		}
		
		
		if(lang != null){
			switch (getLang()){
				
				case "es":
					result = lang.getSpanish();
					break;
				
				case "en":
					result = lang.getEnglish();
					break;
					
				case "fr":
					result = lang.getFrench();
					break;
					
				default:
					result = lang.getEnglish();
					break;
			}
			
		}else{
			result = "<span style='color: red;'>" + identifier + "</span>";
		}
		
		return result;
	}
	
	public void setLang(String lang){
		
		try{
			Variable languages = getEm().createNamedQuery("Variable.findByName", Variable.class).setParameter("name", "languagues").getSingleResult();
			
			for(Value temp : languages.getValues()){
				
				if(temp.getValue() != null && temp.getValue().equals(lang)){
					getCookie().setValue(temp.getValue());
					break;
				}
			}
			
		}catch (IllegalArgumentException ex){
			log.critical("error while finding languages");
			log.exception(ex);
		}catch(Exception ex){
			log.error("error while finding languages");
			log.exception(ex);
		}finally{
			getEm().clear();
		}
	}
}
