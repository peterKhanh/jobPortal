package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.WorkingModel;


public interface WorkingModelService {
	public List<WorkingModel> getAll();
	public Boolean create(WorkingModel workingModel);
	public WorkingModel find(Integer id);
	public Boolean update(WorkingModel workingModel);
	public Boolean delete(Integer id);
	
	public List<WorkingModel> getAllActiveWorkingModel();
	public List<WorkingModel> searchWorkingModel(String keyword);
	//Paging
	Page<WorkingModel> getAll(Integer pageNo);
	Page<WorkingModel>  searchWorkingModel(String keyword, Integer pageNo);
}