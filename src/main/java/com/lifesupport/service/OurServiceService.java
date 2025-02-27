package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.OurService;


public interface OurServiceService {
	public List<OurService> getAll();
	public Boolean create(OurService ourService);
	public OurService find(Integer id);
	public Boolean update(OurService ourService);
	public Boolean delete(Integer id);
	
	public List<OurService> searchOurService(String keyword);
	//Paging
	Page<OurService> getAll(Integer pageNo);
	Page<OurService>  searchOurService(String keyword, Integer pageNo);
}