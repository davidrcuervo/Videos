package com.laetienda.utilities;

import java.util.HashMap;

import javax.persistence.EntityManager;
import com.laetienda.utilities.Logger;
import com.laetienda.entities.Language;
import com.laetienda.entities.User;

/**
 * @author myself
 *
 */
public class Lang {
	
	private HashMap<String, Language> langs;
	private EntityManager em;
	private User user;
	private Logger log;
	private String tempLang;
	
	/**
	 * @param em	entity manager that will be encharged to pull data from the database.
	 * @param user	This user will be used to get the language at the moment to return a phrase
	 * @param log
	 */
	public Lang(EntityManager em, User user, Logger log){
		
		this.log = log;
		this.em = em;
		this.user = user;
		tempLang = "en";
		
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
		
		String result = user.getLanguage().getValue();
		return result.equals("none") ? tempLang : result;
	}
	
	/**
	 * @param user
	 */
	public void setUser(User user){
		this.user = user;
	}
	
	/**
	 * @param tempLang "en" for english.<br />
	 * "es" for spanish.<br />
	 * "fr" for french
	 */
	public void setTempLang(String tempLang){
		this.tempLang = tempLang;
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
				em.clear();
			}catch(Exception ex){
				log.error("The requested text does not exist in the language table. $identifier: " + identifier);
				log.exception(ex);
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
}
