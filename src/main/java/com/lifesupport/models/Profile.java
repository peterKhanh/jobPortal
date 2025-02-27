package com.lifesupport.models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
@Table(name = "profile")

public class Profile {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date createAt;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date updateAt;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date approvedAt;

	private String birthdate;
	private String phone;
	private String address;
	private String url;

	
	@Column(columnDefinition = "LONGTEXT")
	private String skills;
	@Column(columnDefinition = "LONGTEXT")
	private String langugages;
	@Column(columnDefinition = "LONGTEXT")
	private String licences;
	@Column(columnDefinition = "LONGTEXT")
	private String expericences;

	
	private Integer viewCount;
	private Boolean status;

	@ManyToOne
	@JoinColumn(name = "userId")
	User user;

	@ManyToOne
	@JoinColumn(name = "locationId")
	Location location;


	@ManyToOne
	@JoinColumn(name = "workingModelId")
	WorkingModel workingModel;

	
	private String cv_1_Filename;
	
	private String cv_2_Filename;
	
	private String cv_3_Filename;
	
	private String bd_1_Filename;
	
	private String bd_2_Filename;
	
	private String bd_3_Filename;

	
	
	public Profile() {
		super();
	}



	public String getBirthdate() {
		return birthdate;
	}



	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}






	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public Profile(Long id, String title, Date createAt, Date updateAt, Date approvedAt, String birthdate, String phone,
			String address, String url, String skills, String langugages, String licences, String expericences,
			Integer viewCount, Boolean status, User user, Location location, WorkingModel workingModel,
			String cv_1_Filename, String cv_2_Filename, String cv_3_Filename, String bd_1_Filename,
			String bd_2_Filename, String bd_3_Filename) {
		super();
		this.id = id;
		this.title = title;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.approvedAt = approvedAt;
		this.birthdate = birthdate;
		this.phone = phone;
		this.address = address;
		this.url = url;
		this.skills = skills;
		this.langugages = langugages;
		this.licences = licences;
		this.expericences = expericences;
		this.viewCount = viewCount;
		this.status = status;
		this.user = user;
		this.location = location;
		this.workingModel = workingModel;
		this.cv_1_Filename = cv_1_Filename;
		this.cv_2_Filename = cv_2_Filename;
		this.cv_3_Filename = cv_3_Filename;
		this.bd_1_Filename = bd_1_Filename;
		this.bd_2_Filename = bd_2_Filename;
		this.bd_3_Filename = bd_3_Filename;
	}



	public String getCv_1_Filename() {
		return cv_1_Filename;
	}



	public void setCv_1_Filename(String cv_1_Filename) {
		this.cv_1_Filename = cv_1_Filename;
	}



	public String getCv_2_Filename() {
		return cv_2_Filename;
	}



	public void setCv_2_Filename(String cv_2_Filename) {
		this.cv_2_Filename = cv_2_Filename;
	}



	public String getCv_3_Filename() {
		return cv_3_Filename;
	}



	public void setCv_3_Filename(String cv_3_Filename) {
		this.cv_3_Filename = cv_3_Filename;
	}



	public String getBd_1_Filename() {
		return bd_1_Filename;
	}



	public void setBd_1_Filename(String bd_1_Filename) {
		this.bd_1_Filename = bd_1_Filename;
	}



	public String getBd_2_Filename() {
		return bd_2_Filename;
	}



	public void setBd_2_Filename(String bd_2_Filename) {
		this.bd_2_Filename = bd_2_Filename;
	}



	public String getBd_3_Filename() {
		return bd_3_Filename;
	}



	public void setBd_3_Filename(String bd_3_Filename) {
		this.bd_3_Filename = bd_3_Filename;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Date getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(Date approvedAt) {
		this.approvedAt = approvedAt;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getLangugages() {
		return langugages;
	}

	public void setLangugages(String langugages) {
		this.langugages = langugages;
	}

	public String getLicences() {
		return licences;
	}

	public void setLicences(String licences) {
		this.licences = licences;
	}

	public String getExpericences() {
		return expericences;
	}

	public void setExpericences(String expericences) {
		this.expericences = expericences;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public WorkingModel getWorkingModel() {
		return workingModel;
	}

	public void setWorkingModel(WorkingModel workingModel) {
		this.workingModel = workingModel;
	}

	
	
	
	}
