package com.lifesupport.service.Iplm;



 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lifesupport.ConstantsClass;
import com.lifesupport.models.JobApply;
import com.lifesupport.models.User;
import com.lifesupport.repository.JobApplyRepository;
import com.lifesupport.service.JobApplyService;

@Service
public class JobApplyServiceIplm implements JobApplyService {
	@Autowired
	private JobApplyRepository jobApplyRepo;

	int number_of_item_perpage = ConstantsClass.CONST_NUMBER_JOB_PER_PAGE_IN_FRONTEND;

	

	@Override
	public Boolean ApplySingleJob(JobApply jobApply) {
		try {
			this.jobApplyRepo.save(jobApply);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}




	@Override
	public Page<JobApply> getRecentApplyJob(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo - 1, number_of_item_perpage, Sort.by("applyDate").descending());

		return jobApplyRepo.findAll(pageable);
	}


	@Override
	public List<JobApply> getAllApplyJobByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
