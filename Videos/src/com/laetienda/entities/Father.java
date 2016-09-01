package com.laetienda.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.laetienda.utilities.Logger;

public abstract class Father {
	
	protected Logger log;
	private HashMap<String, List<String>> errors;
	
	public Father(Logger log){
		setLogger(log);
	}
	
	public abstract Integer getId();
	public abstract Date getCreated();
	public abstract Date getModified();
	
	public Logger setLogger(Logger log){
		this.log = log;
		
		return this.log;
	}
	
	public HashMap<String, List<String>> getErrors(){
		
		if(errors == null){
			errors = new HashMap<String, List<String>>(); 
		}
		
		return errors;
	}
	
	public void addError(String name, String error){
		
		if(getErrors() == null){
			errors = new HashMap<String, List<String>>();
		}
		
		List<String> errorList = getErrors().get(name);
		
		if(errorList == null){
			errorList = new ArrayList<String>();
			getErrors().put(name, errorList);
		}
		
		errorList.add(error);
	}
}
