package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Slide;


public interface SlideService {
	public List<Slide> getAll();
	public Boolean create(Slide slide);
	public Slide find(Integer id);
	public Boolean update(Slide slide);
	public Boolean delete(Integer id);
	
	public List<Slide> searchSlide(String keyword);
	//Paging
	Page<Slide> getAll(Integer pageNo);
	Page<Slide>  searchSlide(String keyword, Integer pageNo);
}