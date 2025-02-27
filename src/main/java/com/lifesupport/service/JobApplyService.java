package com.lifesupport.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lifesupport.models.Job;
import com.lifesupport.models.JobApply;
import com.lifesupport.models.User;

public interface JobApplyService {
		
	public Boolean ApplySingleJob(JobApply jobApply);
	
	public List<JobApply> getAllApplyJobByUser(User user);
	public Page<JobApply> getRecentApplyJob(Integer pageNo);
	
}
