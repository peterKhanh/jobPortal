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
@Table(name = "enterprise")

public class Enterprise {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "industrialType")
	IndustrialType industrialType;

	private String address;
	private String website;
	
	@Column(columnDefinition = "LONGTEXT")
	private String introduction;

	@Column(name = "number_of_employee")
	private String numberOfEmployee;

	private String bannerName;
	private String logoName;

	private Integer likeCount;
	private Integer followCount;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date createAt;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date updateAt;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm")
	private Date approvedAt;
	private Boolean defaultLogo = true;


	@ManyToOne
	@JoinColumn(name = "userId")
	User user;


	
	public Enterprise(Long id, String name, IndustrialType industrialType, String address, String website,
			String introduction, String numberOfEmployee, String bannerName, String logoName, Integer likeCount,
			Integer followCount, Date createAt, Date updateAt, Date approvedAt, Boolean defaultLogo, User user) {
		super();
		this.id = id;
		this.name = name;
		this.industrialType = industrialType;
		this.address = address;
		this.website = website;
		this.introduction = introduction;
		this.numberOfEmployee = numberOfEmployee;
		this.bannerName = bannerName;
		this.logoName = logoName;
		this.likeCount = likeCount;
		this.followCount = followCount;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.approvedAt = approvedAt;
		this.defaultLogo = defaultLogo;
		this.user = user;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Enterprise() {
		super();
	}


	public Enterprise(Long id, String name, IndustrialType industrialType, String address, String website,
			String introduction, String numberOfEmployee, String bannerName, String logoName, Integer likeCount,
			Integer followCount, Date createAt, Date updateAt, Date approvedAt, Boolean defaultLogo) {
		super();
		this.id = id;
		this.name = name;
		this.industrialType = industrialType;
		this.address = address;
		this.website = website;
		this.introduction = introduction;
		this.numberOfEmployee = numberOfEmployee;
		this.bannerName = bannerName;
		this.logoName = logoName;
		this.likeCount = likeCount;
		this.followCount = followCount;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.approvedAt = approvedAt;
		this.defaultLogo = defaultLogo;
	}



	public Boolean getDefaultLogo() {
		return defaultLogo;
	}


	public void setDefaultLogo(Boolean defaultLogo) {
		this.defaultLogo = defaultLogo;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public IndustrialType getIndustrialType() {
		return industrialType;
	}


	public void setIndustrialType(IndustrialType industrialType) {
		this.industrialType = industrialType;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getWebsite() {
		return website;
	}


	public void setWebsite(String website) {
		this.website = website;
	}


	public String getIntroduction() {
		return introduction;
	}


	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}


	public String getNumberOfEmployee() {
		return numberOfEmployee;
	}


	public void setNumberOfEmployee(String numberOfEmployee) {
		this.numberOfEmployee = numberOfEmployee;
	}


	public String getBannerName() {
		return bannerName;
	}


	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}


	public String getLogoName() {
		return logoName;
	}


	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}


	public Integer getLikeCount() {
		return likeCount;
	}


	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}


	public Integer getFollowCount() {
		return followCount;
	}


	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
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

	
	}
