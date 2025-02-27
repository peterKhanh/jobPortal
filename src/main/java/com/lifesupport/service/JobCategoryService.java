package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.JobCategory;


public interface JobCategoryService {
	public List<JobCategory> getAll();
	public Boolean create(JobCategory category);
	public JobCategory find(Integer id);
	public Boolean update(JobCategory category);
	public Boolean delete(Integer id);
	
	public List<JobCategory> getAllActiveCate();
	public List<JobCategory> searchCate(String keyword);
	//Paging
	Page<JobCategory> getAll(Integer pageNo);
	Page<JobCategory>  searchCate(String keyword, Integer pageNo);
}