package com.o2s.db.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Environment")
public class Environment {

	public static enum Type{
		PROD,
		QA,
		DEV,
		OTHER
	}

	public static enum Status{
		HEALTHY,
		INFO,
		WARNING,
		ERROR
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Type type;
	private String description;
	private String country;
	private String state;
	private String city;
	private Status status;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Device> devices;

	public Environment(){}

	public Environment(String name, String description, Type type) {
		this.name = name;
		this.description = description;
		if(type == null)
			type = Type.OTHER;
		this.type = type;
	}

	public Environment(String name, String description, Type type, String country, String state, String city) {
		this.name = name;
		this.description = description;
		if(type == null)
			type = Type.OTHER;
		this.type = type;
		this.country = country;
		this.state = state;
		this.city = city;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}	
	
}

