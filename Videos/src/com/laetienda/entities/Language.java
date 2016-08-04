package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;;

@Entity
@Table(name="languages")
@NamedQueries({
	@NamedQuery(name="Language.findAll", query="SELECT l FROM Language l"),
	@NamedQuery(name="Language.findByIdentifier", query="SELECT l FROM Language l WHERE l.identifier = :identifier"),
})
public class Language extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LANGUAGES_ID_GENERATOR", sequenceName="LANGUAGES_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LANGUAGES_ID_GENERATOR")
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private String english;

	private String french;
	
	@Column(name="\"identifier\"", unique = true)
	private String identifier;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String spanish;

	//bi-directional many-to-one association to App
	@ManyToOne
	private App app;

	public Language() {
		super(null);
	}

	public Integer getId() {
		return this.id;
	}

	public Date getCreated() {
		return this.created;
	}

	public String getEnglish() {
		return this.english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getFrench() {
		return this.french;
	}

	public void setFrench(String french) {
		this.french = french;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Date getModified() {
		return this.modified;
	}

	public String getSpanish() {
		return this.spanish;
	}

	public void setSpanish(String spanish) {
		this.spanish = spanish;
	}

	public App getApp() {
		return this.app;
	}

	public void setApp(App app) {
		this.app = app;
	}

}