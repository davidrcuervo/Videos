package com.laetienda.entities;

import java.util.Date;
import com.laetienda.utilities.Logger;

public abstract class Father {
	
	private Logger log;
	
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
	
	
}
