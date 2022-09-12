package com.o2s.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.o2s.data.enm.EnvironmentType;
import com.o2s.data.enm.Status;


@Entity
@Table(name="Environment")
public class Environment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private EnvironmentType type;
	private String description;
	private String country;
	private String state;
	private String city;
	private Status status;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name = "env_id")
	@JsonIgnore
	private List<Device> devices = new ArrayList<>();

	public Environment(){}

	public Environment(String name, String description, EnvironmentType type) {
		this.name = name;
		this.description = description;
		if(type == null)
			type = EnvironmentType.OTHER;
		this.type = type;
	}

	public Environment(String name, String description, EnvironmentType type, String country, String state, String city) {
		this.name = name;
		this.description = description;
		if(type == null)
			type = EnvironmentType.OTHER;
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

	public EnvironmentType getType() {
		return type;
	}

	public void setType(EnvironmentType type) {
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

