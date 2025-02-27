package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Tastimonial;


public interface TastimonialService {
	public List<Tastimonial> getAll();
	public Boolean create(Tastimonial tastimonial);
	public Tastimonial find(Integer id);
	public Boolean update(Tastimonial tastimonial);
	public Boolean delete(Integer id);
	public List<Tastimonial> searchTastimonial(String keyword);
	//Paging
	Page<Tastimonial> getAll(Integer pageNo);
	Page<Tastimonial>  searchTastimonial(String keyword, Integer pageNo);
}