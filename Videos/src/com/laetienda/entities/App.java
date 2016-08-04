package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

@Entity
@NamedQueries({
	@NamedQuery(name="App.findAll", query="SELECT a FROM App a"),
	@NamedQuery(name="App.findByName", query="SELECT a FROM App a WHERE a.name = :name"),
})

public class App extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APP_ID_GENERATOR", sequenceName="APP_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APP_ID_GENERATOR")
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String name;

	//bi-directional many-to-one association to Group
	@OneToMany(mappedBy="app")
	private List<Group> groups;

	//bi-directional many-to-one association to Language
	@OneToMany(mappedBy="app")
	private List<Language> languages;

	//bi-directional many-to-one association to Log
	@OneToMany(mappedBy="app")
	private List<Log> logs;

	//bi-directional many-to-one association to Setting
	@OneToMany(mappedBy="app")
	private List<Setting> settings;
	@Transient
	private HashMap<String, String> setting;

	//bi-directional many-to-one association to UserGroup
	@OneToMany(mappedBy="app")
	private List<User> users;
	@Transient
	private HashMap<String, User> userByUsername;
	@Transient
	private HashMap<Integer, User> userById;
	
	//bi-directional many-to-one association to UserGroup
	@OneToMany(mappedBy="app")
	private List<UserGroup> userGroups;

	//bi-directional many-to-one association to Variable
	@OneToMany(mappedBy="app")
	private List<Variable> variables;

	//uni-directional one-to-one association to TextReference
	@OneToOne
	@JoinColumn(name="description")
	private TextReference description;
	
	public App() {
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


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Group addGroup(Group group) {
		getGroups().add(group);
		group.setApp(this);

		return group;
	}

	public Group removeGroup(Group group) {
		getGroups().remove(group);
		group.setApp(null);

		return group;
	}

	public List<Language> getLanguages() {
		return this.languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public Language addLanguage(Language language) {
		getLanguages().add(language);
		language.setApp(this);

		return language;
	}

	public Language removeLanguage(Language language) {
		getLanguages().remove(language);
		language.setApp(null);

		return language;
	}

	public List<Log> getLogs() {
		return this.logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public Log addLog(Log log) {
		getLogs().add(log);
		log.setApp(this);

		return log;
	}

	public Log removeLog(Log log) {
		getLogs().remove(log);
		log.setApp(null);

		return log;
	}
	
	public String getSetting(String setting){
		String result = null;
		
		if(this.setting == null){
			this.setting = new HashMap<String, String>();
		}
		
		String temp = this.setting.get(setting);		
		if(temp == null || temp.isEmpty()){
			
			for(Setting temp2 : getSettings()){
				if(temp2.getSetting().getVariable().getName().equals(setting)){
					result = temp2.getSetting().getValue();
					this.setting.put(setting, result);
					break;
				}
			}
			
		}else{
			result = temp;
		}
		
		return result;
	}
	
	public List<Setting> getSettings() {
		return this.settings;
	}

	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}

	public Setting addSetting(Setting setting) {
		getSettings().add(setting);
		setting.setApp(this);

		return setting;
	}

	public Setting removeSetting(Setting setting) {
		getSettings().remove(setting);
		setting.setApp(null);

		return setting;
	}
	
	public User getUser(String username){
		User result = null;
		
		if(this.userByUsername == null){
			this.userByUsername = new HashMap<String, User>();
		}
		
		User temp = this.userByUsername.get(username);
		if(temp == null){
			
			for(int c=0; c < getUsers().size(); c++){
				
				if(getUsers().get(c).getUsername().equals(username)){
					if(getUsers().get(c).getApp().equals(this)){
						System.out.println("objects are the same");
					}else{
						System.out.println("objects are NOT the same");
					}
					break;
				}
			}
			
			for(User temp2 : getUsers()){
				if(temp2.getUsername().equals(username)){
					result = temp2;
					this.userByUsername.put(result.getUsername(), result);
					
					
					
					break;
				}
			}
			
		}else{
			result = temp;
		}
		
		return result;
	}
	
	public User getUser(Integer id){
		User result = null;
		
		if(this.userById == null){
			this.userById = new HashMap<Integer, User>();
		}
		
		User temp = this.userById.get(id);
		if(temp == null){
			
			for(User temp2 : getUsers()){
				if(temp2.getId().equals(id)){
					result = temp2;
					this.userById.put(result.getId(), result);
					break;
				}
			}
			
		}else{
			result = temp;
		}
		
		return result;
	}
	
	public List<User> getUsers(){
		return this.users;
	}

	public List<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public UserGroup addUserGroup(UserGroup userGroup) {
		getUserGroups().add(userGroup);
		userGroup.setApp(this);

		return userGroup;
	}

	public UserGroup removeUserGroup(UserGroup userGroup) {
		getUserGroups().remove(userGroup);
		userGroup.setApp(null);

		return userGroup;
	}

	public List<Variable> getVariables() {
		return this.variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public Variable addVariable(Variable variable) {
		getVariables().add(variable);
		variable.setApp(this);

		return variable;
	}

	public Variable removeVariable(Variable variable) {
		getVariables().remove(variable);
		variable.setApp(null);

		return variable;
	}

	public TextReference getDescription() {
		return this.description;
	}

	public void setDescription(TextReference description) {
		this.description = description;
	}

}