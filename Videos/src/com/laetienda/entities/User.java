package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.laetienda.utilities.Mail;

@Entity
@Table(name="users")
@NamedQueries({
		@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
		@NamedQuery(name="User.findByUsername", query="SELECT u FROM User u WHERE u.username = :username"),
})
public class User extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERS_ID_GENERATOR", sequenceName="USERS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERS_ID_GENERATOR")
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)	
	private Date created;

	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)	
	private Date modified;

	private String password;

	private String username;

	//bi-directional many-to-one association to App
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"app_id\"")
	private App app;
	
	//bi-directional many-to-one association to UserGroup
	@OneToMany(mappedBy="user")
	private List<UserGroup> userGroups;

	//uni-directional one-to-one association to TextReference
	@OneToOne
	@JoinColumn(name="description")
	private TextReference description;
	
	//bi-directional many-to-one association to UserGroup
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Log> logs;

	//uni-directional one-to-one association to Value
	@OneToOne
	@JoinColumn(name="language")
	private Value language;
	
	//uni-directional one-to-one association to status
	@OneToOne
	@JoinColumn(name="\"status\"")
	private Value status;
	
	@Transient
	private Mail mail;

	public User() {
		super(null);
	}

	public Integer getId() {
		return this.id;
	}

	public Date getCreated() {
		return this.created;
	}

	public Date getModified() {
		return this.modified;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password, String password2) {
		
		if(password != null && !password.isEmpty()){
			this.password = org.apache.catalina.realm.RealmBase.Digest(password, "SHA-256", "utf-8");
			
			if(this.password.length() > 254){
				addError("password", "password_error_long");
			}
			
			if(password.equals(password2)){
				addError("password", "password_error_different");
			}
			
		}else{
			addError("password", "password_error_empty");
		}
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		
		if(username != null && !username.isEmpty()){
			
			if(username.length() > 254){
				addError("username", "username_error_long");
			}
			
			Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			Matcher m = p.matcher(username);
			
			if(!m.matches()){
				addError("username", "username_error_invalid");
			}
			
			if(getApp().getUser(username) != null){
				addError("username", "username_error_exist");
			}
			
		}else{
			addError("username", "username_error_empty");
		}
		
		this.username = username;
	}

	public App getApp() {
		return this.app;
	}
	
	public void setApp(App app){
		this.app = app;
		
		if(!getApp().getUsers().contains(this)){
			getApp().addUser(this);
		}
	}

	public List<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public UserGroup addUserGroup(UserGroup userGroup) {
		getUserGroups().add(userGroup);
		userGroup.setUser(this);

		return userGroup;
	}

	public UserGroup removeUserGroup(UserGroup userGroup) {
		getUserGroups().remove(userGroup);
		userGroup.setUser(null);

		return userGroup;
	}

	public TextReference getDescription() {
		return this.description;
	}

	public void setDescription(TextReference description) {
		this.description = description;
	}

	public Value getLanguage() {
		return this.language;
	}

	public void setLanguage(Value language) {
		this.language = language;
	}
	
	public Value getStatus(){
		return this.status;
	}
	
	public void setStatus(String status){
		
		if(getErrors().size() <= 0){
			List<Value> values = getApp().getValues("user_status");
			
			if(!(values == null)){
				
				boolean flag = false;
				for(Value temp : values){
					if(temp.getValue().equals(status)){
						
						flag = true;
						
						switch (status){
						
						case "registered":
							if(mail != null && send()){
								setStatus(temp);
							}else{
								addError("user", "user_error_internal");
								log.critical("It was not possible to send email");
							}
							break;
						
						default:
							addError("user", "user_error_internal");
							log.critical("status does not exist. $status: " + status);
							break;
						}
						
						break;
					}
				}
				
				if(!flag){
					log.critical("User status doesn't exist in the database. $user_status: " + status);
					addError("user", "user_error_internal");
				}
				
			}else{
				log.critical("Variable does not exist in the database. $variable: user_status");
				addError("user", "user_error_internal");
			}
		}
	}
	
	private boolean send(){
		
		//TODO find a way to get content and subject		
		
		/*
		mail.setContent(content);
		mail.setSubject(subject);
		
		return mail.send();
		*/
		return true;
	}
	
	public void setStatus(Value status){
		this.status = status;
	}
	
	public List<Log> getLogs(){
		return this.logs;
	}
	
	public void setMail(Mail mail){
		
		if(mail != null){
			this.mail = mail;
		}else{
			addError("user", "user_error_internal");
		}
	}
	
	public Log addLog(Log log){
		
		getLogs().add(log);
		log.setUser(this);
		
		if(log.getApp() == null){
			log.setApp(getApp());
		}
		
		return log;
	}

}