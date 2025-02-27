package com.lifesupport.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifesupport.models.Job;
import com.lifesupport.models.JobApply;
import com.lifesupport.models.User;
import com.lifesupport.repository.JobRepository;
import com.lifesupport.repository.UserRepository;
import com.lifesupport.service.JobApplyService;
import com.lifesupport.service.UserService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/jobapply")
public class JobApplyController {

	@Autowired
	private UserService userService;


	@Autowired
	private JobApplyService jobApplyService;

	@Autowired
	private UserRepository repoUser;
	@Autowired
	private JobRepository jobRepo;

	@ResponseBody
	@Transactional
	@RequestMapping("/apply/{id}")
	public String applyjob(Principal principal, @PathVariable("id") Long id) {
		Job job = jobRepo.findById(id).get();
		Date createAt = new Date();
		JobApply jobApply = new JobApply();

		if (principal != null) {
			System.out.println("Da login");
			
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);

			jobApply.setUser(loggedUser);
			jobApply.setJob(job);
			jobApply.setApplyDate(createAt);	
			
			//Update ApplyCount for Job 
			int v_applyCount = 0;
			if (job.getApplyCount() != null) {
				v_applyCount = job.getApplyCount() + 1;
			}else {
				v_applyCount = 1;
			}
			job.setApplyCount(v_applyCount);
			jobRepo.save(job);
			
			boolean info = jobApplyService.ApplySingleJob(jobApply);
			if (info = true ) {
				return "Ung tuyen thanh cong";
			}else {
				return "Khong thanh cong";
			}
		} else {
			return "Chua dang nhap";

		}
	}
}
