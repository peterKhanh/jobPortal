package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.IndustrialType;


public interface IndustrialTypeService {
	public List<IndustrialType> getAll();
	public Boolean create(IndustrialType  industrialType);
	public IndustrialType find(Integer id);
	public Boolean update(IndustrialType industrialType);
	public Boolean delete(Integer id);
	
	public List<IndustrialType> getAllActiveIndustrialType();
	public List<IndustrialType> searchIndustrialType(String keyword);
	//Paging
	Page<IndustrialType> getAll(Integer pageNo);
	Page<IndustrialType>  searchCate(String keyword, Integer pageNo);
}