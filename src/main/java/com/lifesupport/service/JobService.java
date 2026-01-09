package com.lifesupport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Enterprise;
import com.lifesupport.models.Job;

public interface JobService {
		
		public List<Job> searchJob(String keyword);
		Page<Job> getAll(Integer pageNo);
		Page<Job> searchJob(String keyword, Integer pageNo);
		Page<Job> getAllByLocation(Integer pageNo, Integer locationId);

		public List<Job> GetAllActiveJobByEnterprise(Enterprise enterprise);
		public List<Job> GetAllExpiredJobByEnterprise(Enterprise enterprise);
	
		public List<Job> GetAllJobOfEnterpriseByStatus(Enterprise enterprise, String status);

		Optional<Job> getSingleJob(Long id);
	
		Page<Job> getApprovedJobByCategory(Integer pageNo, Integer cateId);
		
		Page<Job> getApprovedJobByLocation(Integer pageNo, Integer locationId);
			
		Page<Job> getAllJobForHomePage(Integer pageNo);

		
}
