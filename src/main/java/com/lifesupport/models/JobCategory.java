package com.lifesupport.models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "jobcategory")

public class JobCategory {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(name = "iconName")
	private String iconName;
	@Column(name = "status")
	private Boolean status;
	@Column(name = "popular")
	private Boolean popular = false;

	public JobCategory() {
		super();
	}


	public JobCategory(Integer id, String name, String iconName, Boolean status, Boolean popular) {
		super();
		this.id = id;
		this.name = name;
		this.iconName = iconName;
		this.status = status;
		this.popular = popular;
	}


	public Boolean getPopular() {
		return popular;
	}


	public void setPopular(Boolean popular) {
		this.popular = popular;
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

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
