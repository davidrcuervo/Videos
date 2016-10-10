package com.laetienda.beans;

import java.util.List;
import java.util.HashMap;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import com.laetienda.utilities.Logger;

public class Page {

	private List<String> styles;
	private List<String> scripts;
	private List<String> onloads;
	private HashMap<String, String> tabs;
	private Logger log;
	private String url;
	
	public Page(HttpServletRequest request, Logger log){
		styles = new ArrayList<String>();
		scripts = new ArrayList<String>();
		tabs = new HashMap<String, String>();
		this.log = log;
		
		buildUrl(request);
	}
	
	private void buildUrl(HttpServletRequest request){
		
		int port = request.getServerPort();
		url = request.getHeader("X-Forwarded-Proto") == null ? "http" : request.getHeader("X-Forwarded-Proto");
		
		url += "://" + request.getServerName();
		//url = request.getScheme() + "://" + request.getServerName();
		
		if(!(port == 80 || port == 443)){
			url += ":" + Integer.toString(port);
		}
		
		url += request.getContextPath();
	}
	
	public String getUrl(){
		return url;
	}
	
	public List<String> getStyles(){
		return this.styles;
	}
	
	public void addStyle(String style){
		log.info("Adding style. $style: " + style);
		
		if(getStyles().contains(style)){
			log.debug("This style has been loaded previosly. $style: " + style);
		}else{
			//TODO Validate style format
			getStyles().add(style);
		}
	}
	
	public List<String> getScripts(){
		return this.scripts;
	}
	
	public void addScript(String script){
		log.info("Adding script. $script: " + script);
		
		if(getScripts().contains(script)){
			log.debug("This script has been loaded previously. $script: " + script);
		}else{
			//TODO validate script format
			getScripts().add(script);
		}
	}
	
	public List<String> getOnloads(){
		return this.onloads;
	}
	
	public void addOnload(String onload){
		log.info("Adding onload function. $onload: " + onload);
		
		if(getOnloads().contains(onload)){
			log.debug("This onload function has been loaded previously. $onload: " + onload);
		}else{
			//TODO validate onload function
			getOnloads().add(onload);
		}
	}
	
	public String encode(int original){
		return encode(Integer.toString(original));
	}
	
	public String encode(String original){
		String result = new String();
		
		try{
			byte[] encrypted = Base64.getEncoder().encode(original.getBytes());
			String temp = new String(encrypted);
			result = URLEncoder.encode(temp, "UTF-8");
		}catch(Exception ex){
			log.error("error while encodig string");
			log.exception(ex);
		}
		
		return result;
	}
	
	public String decode(String encoded){
		String result = new String();
		
		try{
			String encrypted = URLDecoder.decode(encoded, "UTF-8");
			
			byte[] decoded = Base64.getDecoder().decode(encrypted);
			result = new String(decoded);
		}catch(Exception ex){
			log.error("error while decoding string");
			log.exception(ex);
		}
		
		return result;
	}
	
	private HashMap<String, String> getTabs(){
		return tabs;
	}
	
	public void addTab(String identifier, String value){
		log.info("Setting active tab. $identifier: " + identifier + ", $value: " + value);
		
		getTabs().put(identifier, value);
	}
	
	public String showTab(String identifier){
		log.info("Getting active tab. $identifier: " + identifier);
		
		return getTabs().get(identifier);
	}
}
