package com.laetienda.utilities;

import com.laetienda.entities.User;

public class Mailer {

	private Logger log;
	private String server;
	private String username;
	private String password;
	private int port;
	
	//private String subject;
	//private String content;
	//private String to;
	//private Properties mailServerProperties;
	//private Session getMailSession;
	//private MimeMessage generateMailMessage;
	
	public Mailer(String server, int port, String username, String password, Logger log){
		this.log = log;
		
		this.server = server;
		this.port = port;
		this.username = username;
		this.password = password;
		
		this.log.info("Setting up mailer. "
				+ "$server: " + server
				+ " $port: " + Integer.toString(port)
				+ " $username: " + username
				+ " $password: **********");
	}
	
	public Mail createMail(User user, Logger log){
		Mail mail = new Mail(server, port, username, password, user, log);
		
		return mail;
	}
}
