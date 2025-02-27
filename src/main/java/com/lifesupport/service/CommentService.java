package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Comment;


public interface CommentService {
//	public List<Slide> getAll();
	public Boolean create(Comment comment);
	public Comment find(Integer id);
	public Boolean update(Comment comment);
	public Boolean delete(Integer id);
	
//	public List<Slide> searchSlide(String keyword);
	//Paging
//	Page<Slide> getAll(Integer pageNo);
//	Page<Slide>  searchSlide(String keyword, Integer pageNo);
}