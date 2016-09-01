package com.laetienda.utilities;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.laetienda.entities.User;

public class Mail {
	
	private Logger log;
	private String server;
	private String username;
	private String password;
	private int port;
	private String subject;
	private String content;
	private User user;
	private Properties mailServerProperties;
	private Session getMailSession;
	private MimeMessage generateMailMessage;
	
	public Mail(String server, int port, String username, String password, User user, Logger log){
		
		this.log = log;
		
		this.server = server;
		this.port = port;
		this.username = username;
		this.password = password;
		this.user = user;
	}
	
	public void setSubject(String subject){
		this.subject = subject;
	}
	
	public String getSubject(){
		return this.subject;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public boolean send(){
		boolean result = false;
		
		try{	
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", Integer.toString(port));
			mailServerProperties.put("mail.smtp.auth", "true");
			//mailServerProperties.put("mail.smtp.starttls.enable", "true");
			mailServerProperties.put("mail.smtp.ssl.enable", "true");
			mailServerProperties.put("mail.smtp.ssl.trust", "*");
			
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			log.debug("TO: " + getUser().getUsername());
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(getUser().getUsername()));
			generateMailMessage.setSubject(getSubject(), "utf-8");
			
			generateMailMessage.setContent(getContent(), "text/html; charset=utf-8");
			
			Transport transport = getMailSession.getTransport("smtp");
			transport.connect(server, username, password);
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
			
			result = true;
			
		}catch (Exception ex){
			log.notice("Exception caught while sending email message");
			log.exception(ex);
		}finally{
			
		}
		
		return result;
	}
}
