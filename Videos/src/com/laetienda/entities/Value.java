package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="values")
@NamedQuery(name="Value.findAll", query="SELECT v FROM Value v")
public class Value extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="VALUES_ID_GENERATOR", sequenceName="VALUES_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="VALUES_ID_GENERATOR")
	private Integer id;
	
	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String value;

	//bi-directional many-to-one association to Variable
	@ManyToOne
	private Variable variable;

	//uni-directional one-to-one association to TextReference
	@OneToOne
	@JoinColumn(name="description")
	private TextReference description;

	public Value() {
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Variable getVariable() {
		return this.variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public TextReference getDescription() {
		return this.description;
	}

	public void setDescription(TextReference description) {
		this.description = description;
	}

}