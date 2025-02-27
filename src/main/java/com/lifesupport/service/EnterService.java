package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Enterprise;

public interface EnterService {
	public List<Enterprise> searchEnterprise(String keyword);

	//Paging
		Page<Enterprise> getAll(Integer pageNo);
		Page<Enterprise> searchEnterprise(String keyword, Integer pageNo);
		Page<Enterprise> getAllByType(Integer pageNo, Integer industrialTypeId);

}
