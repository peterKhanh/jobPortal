package com.lifesupport.models;

public class AdminDashBoard {
	private Integer totalCompany;
	private Integer totalJobActive;
	private Integer totalCandidate;

	public AdminDashBoard() {
		
	}

	public Integer getTotalCompany() {
		return totalCompany;
	}

	public AdminDashBoard(Integer totalCompany, Integer totalJobActive, Integer totalCandidate) {
		super();
		this.totalCompany = totalCompany;
		this.totalJobActive = totalJobActive;
		this.totalCandidate = totalCandidate;
	}

	public void setTotalCompany(Integer totalCompany) {
		this.totalCompany = totalCompany;
	}

	public Integer getTotalJobActive() {
		return totalJobActive;
	}

	public void setTotalJobActive(Integer totalJobActive) {
		this.totalJobActive = totalJobActive;
	}

	public Integer getTotalCandidate() {
		return totalCandidate;
	}

	public void setTotalCandidate(Integer totalCandidate) {
		this.totalCandidate = totalCandidate;
	}

}
