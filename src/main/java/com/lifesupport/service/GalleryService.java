package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Blog;
import com.lifesupport.models.Gallery;


public interface GalleryService {
	public List<Gallery> getAll();
	public Boolean create(Gallery gallery);
	public Gallery find(Integer id);
	public Boolean update(Gallery gallery );
	public Boolean delete(Integer id);
	
	public List<Gallery> searchGallery(String keyword);
	//Paging
	Page<Gallery> getAll(Integer pageNo);
	Page<Gallery> searchGallery(String keyword, Integer pageNo);
	Page<Gallery> getAllByType(Integer pageNo, String type);
	Page<Gallery> getByType(Integer pageNo, String type);
}