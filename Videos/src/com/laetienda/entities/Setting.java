package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="settings")
@NamedQueries({
	@NamedQuery(name="Setting.findAll", query="SELECT s FROM Setting s"),
	@NamedQuery(name="Setting.findByVariable", query="SELECT s FROM Setting s WHERE s.setting.variable.name = :variable")
})
public class Setting extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SETTINGS_ID_GENERATOR", sequenceName="SETTINGS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SETTINGS_ID_GENERATOR")
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

	//uni-directional one-to-one association to Value
	@OneToOne
	@JoinColumn(name="setting")
	private Value setting;

	public Setting() {
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

	public Value getSetting() {
		return this.setting;
	}

	public void setSetting(Value setting) {
		this.setting = setting;
	}
}