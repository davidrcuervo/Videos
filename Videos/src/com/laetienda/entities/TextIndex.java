package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="texts_indexes")
@NamedQuery(name="TextIndex.findAll", query="SELECT t FROM TextIndex t")
public class TextIndex extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TEXTS_INDEXES_ID_GENERATOR", sequenceName="TEXTS_INDEXES_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TEXTS_INDEXES_ID_GENERATOR")
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	//bi-directional many-to-one association to Text
	@OneToMany(mappedBy="textsIndexe")
	private List<Text> texts;

	public TextIndex() {
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

	public List<Text> getTexts() {
		return this.texts;
	}

	public void setTexts(List<Text> texts) {
		this.texts = texts;
	}

	public Text addText(Text text) {
		getTexts().add(text);
		text.setTextsIndexe(this);

		return text;
	}

	public Text removeText(Text text) {
		getTexts().remove(text);
		text.setTextsIndexe(null);

		return text;
	}

}