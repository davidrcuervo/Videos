package com.laetienda.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="variables")
@NamedQuery(name="Variable.findAll", query="SELECT v FROM Variable v")
public class Variable extends Father implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="VARIABLES_ID_GENERATOR", sequenceName="VARIABLES_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="VARIABLES_ID_GENERATOR")
	private Integer id;

	@Column(name="\"created\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Column(name="\"modified\"", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String name;

	//bi-directional many-to-one association to App
	@ManyToOne
	private App app;

	//bi-directional many-to-one association to Value
	@OneToMany(mappedBy="variable")
	private List<Value> values;

	//uni-directional one-to-one association to TextReference
	@OneToOne
	@JoinColumn(name="description")
	private TextReference description;

	public Variable() {
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

	public App getApp() {
		return this.app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public List<Value> getValues() {
		return this.values;
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

	public Value addValue(Value value) {
		getValues().add(value);
		value.setVariable(this);

		return value;
	}

	public Value removeValue(Value value) {
		getValues().remove(value);
		value.setVariable(null);

		return value;
	}

	public TextReference getDescription() {
		return this.description;
	}

	public void setDescription(TextReference description) {
		this.description = description;
	}

}