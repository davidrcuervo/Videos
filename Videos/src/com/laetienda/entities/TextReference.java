package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="text_references")
@NamedQuery(name="TextReference.findAll", query="SELECT t FROM TextReference t")
public class TextReference extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TEXT_REFERENCES_ID_GENERATOR", sequenceName="TEXT_REFERENCE_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TEXT_REFERENCES_ID_GENERATOR")
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	//uni-directional one-to-one association to TextIndex
	@OneToOne
	@JoinColumn(name="spanish")
	private TextIndex spanish;

	//uni-directional one-to-one association to TextIndex
	@OneToOne
	@JoinColumn(name="english")
	private TextIndex english;

	//uni-directional one-to-one association to TextIndex
	@OneToOne
	@JoinColumn(name="french")
	private TextIndex french;

	public TextReference() {
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

	public TextIndex getSpanish() {
		return this.spanish;
	}

	public void setSpanish(TextIndex spanish) {
		this.spanish = spanish;
	}

	public TextIndex getEnglish() {
		return this.english;
	}

	public void setEnglish(TextIndex english) {
		this.english = english;
	}

	public TextIndex getFrench() {
		return this.french;
	}

	public void setFrench(TextIndex french) {
		this.french = french;
	}
}