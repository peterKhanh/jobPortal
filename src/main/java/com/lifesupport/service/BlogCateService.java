package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.BlogCate;


public interface BlogCateService {
	public List<BlogCate> getAll();
	public Boolean create(BlogCate blogCate);
	public BlogCate find(Integer id);
	public Boolean update(BlogCate blogCate);
	public Boolean delete(Integer id);
	
	public List<BlogCate> getAllActiveBlogCate();
	public List<BlogCate> searchBlogCate(String keyword);
	//Paging
	Page<BlogCate> getAll(Integer pageNo);
	Page<BlogCate>  searchBlogCate(String keyword, Integer pageNo);
}