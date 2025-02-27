package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Location;


public interface LocationService {
	public List<Location> getAll();
	public Boolean create(Location location);
	public Location find(Integer id);
	public Boolean update(Location location);
	public Boolean delete(Integer id);
	
	public List<Location> getAllActiveLocation();
	public List<Location> searchLocation(String keyword);
	//Paging
	Page<Location> getAll(Integer pageNo);
	Page<Location>  searchCate(String keyword, Integer pageNo);
}