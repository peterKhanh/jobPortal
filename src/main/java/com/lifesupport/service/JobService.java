package com.lifesupport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.lifesupport.models.Enterprise;
import com.lifesupport.models.Job;
import com.lifesupport.models.Location;
import com.lifesupport.models.JobCategory;

public interface JobService {
		
		public List<Job> searchJob(String keyword);
		
		Page<Job> getAll(Integer pageNo);

		Page<Job> getAllApprovedJob(Integer pageNo);
		Page<Job> getAllApprovedJobByKeyword(Integer pageNo, String keyword);
		Page<Job> getAllApprovedJobByKeywordAndLocation(Integer pageNo, String keyword, Location location);
		Page<Job> getAllApprovedJobByLocation(Integer pageNo, Location location);







		Page<Job> getApprovedJobByCategory(Integer pageNo, Integer cateId);




		Page<Job> searchJob(String keyword, Integer pageNo);

		public List<Job> GetAllActiveJobByEnterprise(Enterprise enterprise);
		public List<Job> GetAllExpiredJobByEnterprise(Enterprise enterprise);
	
		public List<Job> GetAllJobOfEnterpriseByStatus(Enterprise enterprise, String status);

		Optional<Job> getSingleJob(Long id);
	
		
		Page<Job> getApprovedJobByLocation(Integer pageNo, Integer locationId);
			
		Page<Job> getAllJobForHomePage(Integer pageNo);

		
}
