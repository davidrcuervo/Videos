package com.laetienda.utilities;

import javax.persistence.EntityManager;

import com.laetienda.utilities.DB;
import com.laetienda.utilities.Logger;

public class DbTransaction {
	
	private EntityManager em;
	private Logger log;
	private DB db;
	
	public DbTransaction(DB db, Logger log){
		this.db = db;
		try{
			em = db.getEm();
			em.getTransaction().begin();
			this.log = log;
		}catch(Exception ex){
			log.critical("Error while starting database transaction");
			log.exception(ex);
		}
	}
	
	public boolean commit(){
		log.info("comminting changes");
		
		boolean result = false;
		
		try{
			
			em.flush();
			em.getTransaction().commit();
			result = true;
			
		}catch(Exception ex){
			
			try{
				em.getTransaction().rollback();
			}catch(Exception ex1){
				log.error("Exception caught while rolling back");
				log.exception(ex1);
			}
			
			log.notice("Exception caught while commiting in the database");
			log.exception(ex);
		}finally{
			close();
		}
		return result;
	}
	
	public void close(){
		log.info("Closing entity manager");
		db.closeEm(em);
	}
	
	public EntityManager getEm(){
		return this.em;
	}
	
}
