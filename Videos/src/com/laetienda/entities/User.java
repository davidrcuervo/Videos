package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public App getApp() {
		return this.app;
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
	
	public void setStatus(Value status){
		this.status = status;
	}
	
	public List<Log> getLogs(){
		return this.logs;
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