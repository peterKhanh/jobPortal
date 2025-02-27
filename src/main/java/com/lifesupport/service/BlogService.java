package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Blog;
import com.lifesupport.models.Job;

public interface BlogService {
	public List<Blog> searchBlog(String keyword);

	//Paging
		Page<Blog> getAll(Integer pageNo);
		Page<Blog> searchBlog(String keyword, Integer pageNo);
		Page<Blog> getAllByCate(Integer pageNo, Integer cateId);


}
