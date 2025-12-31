package com.lifesupport.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "Job")
public class Job {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "TEXT")
	private String title;
	private Integer numberOfRecruitement;	// 20 employee
	private String yearOfExperience;		// Over 3 years  - 2 - 4 years
	private String trialTime;				// 2 months	
	private String salary;					// 25 Man 
	private String address;	
	private String workingAddress; 
	private String workingTime; 			// 8AM to 17 PM
	private String gender;					// MALE - FEMALE - Not required
	private String ageRange;
	@Column(columnDefinition = "LONGTEXT")
	private String reponsibility;
	@Column(columnDefinition = "LONGTEXT")
	private String description;
	@Column(columnDefinition = "LONGTEXT")
	private String benefit;

	
	private Integer viewCount;
	private Integer likeCount;
	private Integer applyCount;			// number of CV applied	
	private String status;				//APPROVED - BLOCKED - EDITTING

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date createAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date updateAt;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date expiredDate;

	//
	@ManyToOne
	@JoinColumn(name = "enterpriseId")
	Enterprise enterprise;

	@ManyToOne
	@JoinColumn(name = "jobcategoryId")
	JobCategory jobCategory;

	@ManyToOne
	@JoinColumn(name = "locationId")
	Location location;


	@ManyToOne
	@JoinColumn(name = "workingModelId")
	WorkingModel workingModel;

	// Contructor
	public Job() {
		super();
	}

	public JobCategory getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(JobCategory jobCategory) {
		this.jobCategory = jobCategory;
	}

	public Job(Long id, String title, Integer numberOfRecruitement, String yearOfExperience, String trialTime,
			String salary, String address, String workingAddress, String workingTime, String gender, String ageRange,
			String reponsibility, String description, String benefit, Integer viewCount, Integer likeCount,
			Integer applyCount, String status, Date createAt, Date updateAt, Date expiredDate, Enterprise enterprise,
			JobCategory jobCategory, Location location,  WorkingModel workingModel) {
		super();
		this.id = id;
		this.title = title;
		this.numberOfRecruitement = numberOfRecruitement;
		this.yearOfExperience = yearOfExperience;
		this.trialTime = trialTime;
		this.salary = salary;
		this.address = address;
		this.workingAddress = workingAddress;
		this.workingTime = workingTime;
		this.gender = gender;
		this.ageRange = ageRange;
		this.reponsibility = reponsibility;
		this.description = description;
		this.benefit = benefit;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.applyCount = applyCount;
		this.status = status;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.expiredDate = expiredDate;
		this.enterprise = enterprise;
		this.jobCategory = jobCategory;
		this.location = location;
		this.workingModel = workingModel;
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

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Integer getNumberOfRecruitement() {
		return numberOfRecruitement;
	}

	public void setNumberOfRecruitement(Integer numberOfRecruitement) {
		this.numberOfRecruitement = numberOfRecruitement;
	}

	public String getYearOfExperience() {
		return yearOfExperience;
	}

	public void setYearOfExperience(String yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}

	public String getTrialTime() {
		return trialTime;
	}

	public void setTrialTime(String trialTime) {
		this.trialTime = trialTime;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	public String getReponsibility() {
		return reponsibility;
	}

	public void setReponsibility(String reponsibility) {
		this.reponsibility = reponsibility;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
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

	public String getWorkingAddress() {
		return workingAddress;
	}

	public void setWorkingAddress(String workingAddress) {
		this.workingAddress = workingAddress;
	}

	public String getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}


	
}
