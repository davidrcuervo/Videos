package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="groups")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GROUPS_ID_GENERATOR", sequenceName="GROUPS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GROUPS_ID_GENERATOR")
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	private String group;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	//bi-directional many-to-one association to App
	@ManyToOne
	private App app;

	//bi-directional many-to-one association to UserGroup
	@OneToMany(mappedBy="group")
	private List<UserGroup> userGroups;

	//uni-directional one-to-one association to TextReference
	@OneToOne
	@JoinColumn(name="description")
	private TextReference description;

	public Group() {
		super(null);
	}

	public Integer getId() {
		return this.id;
	}

	public Date getCreated() {
		return this.created;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Date getModified() {
		return this.modified;
	}

	public App getApp() {
		return this.app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public List<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public UserGroup addUserGroup(UserGroup userGroup) {
		getUserGroups().add(userGroup);
		userGroup.setGroupBean(this);

		return userGroup;
	}

	public UserGroup removeUserGroup(UserGroup userGroup) {
		getUserGroups().remove(userGroup);
		userGroup.setGroupBean(null);

		return userGroup;
	}

	public TextReference getDescription() {
		return this.description;
	}

	public void setDescription(TextReference description) {
		this.description = description;
	}

}