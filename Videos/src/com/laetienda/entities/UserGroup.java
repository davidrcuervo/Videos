package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_groups")
@NamedQuery(name="UserGroup.findAll", query="SELECT u FROM UserGroup u")
public class UserGroup extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USER_GROUPS_ID_GENERATOR", sequenceName="USER_ROLES_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_GROUPS_ID_GENERATOR")
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	//bi-directional many-to-one association to App
	@ManyToOne
	private App app;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	public UserGroup() {
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

	public App getApp() {
		return this.app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroupBean(Group group) {
		this.group = group;
	}

}