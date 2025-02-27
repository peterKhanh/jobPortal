package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Category;


public interface CategoryService {
	public List<Category> getAll();
	public Boolean create(Category category);
	public Category find(Integer id);
	public Boolean update(Category category);
	public Boolean delete(Integer id);
	
	public List<Category> getAllActiveCate();
	public List<Category> searchCate(String keyword);
	//Paging
	Page<Category> getAll(Integer pageNo);
	Page<Category>  searchCate(String keyword, Integer pageNo);
}