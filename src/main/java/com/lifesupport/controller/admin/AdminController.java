package com.lifesupport.controller.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lifesupport.models.AdminDashBoard;
import com.lifesupport.models.Job;
import com.lifesupport.models.JobApply;
import com.lifesupport.models.User;
import com.lifesupport.repository.UserRepository;
import com.lifesupport.service.JobApplyService;
import com.lifesupport.service.JobService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserRepository repoUser;
	@Autowired
	private JobService jobService;
	@Autowired
	private JobApplyService jobApplyService;

	@GetMapping
	public String index() {
		return "redirect:/admin/lte3";
	}

	@RequestMapping("/")
	public String homeAdmin(Model model, Principal principal) {
//		List<Product> listproduct_recent = repoProduct.findTop3ByOrderByCreateAtDesc();
//		model.addAttribute("listproduct_recent", listproduct_recent);

		if (principal != null) {
			String userName = principal.getName();
			System.out.println("Current Logged in User is: " + userName);
			User loggedUser = repoUser.findByUserName(userName);
			System.out.println("Current Logged in FullName is: " + loggedUser.getFullName());
			model.addAttribute("loggedUser", loggedUser);
			System.out.println("Home 1");

		} else {
			System.out.println("HOme 1");
			System.out.println("Not Login: ");
		}

		return "admin/admin_index";
	}

	@RequestMapping("/lte3")
	public String homeAdminLte3(Model model, Principal principal) {
		
		
		AdminDashBoard adminDashBoard = new AdminDashBoard(20, 57, 89);
		model.addAttribute("adminDashBoard", adminDashBoard);


		if (principal != null) {
			String userName = principal.getName();
			System.out.println("Current Logged in User is: " + userName);
			User loggedUser = repoUser.findByUserName(userName);
			System.out.println("Current Logged in FullName is: " + loggedUser.getFullName());
			model.addAttribute("loggedUser", loggedUser);

			Page<Job> recent_jobs = jobService.getAll(1);
			model.addAttribute("recent_jobs", recent_jobs);

			Page<JobApply> recent_apply_jobs = jobApplyService.getRecentApplyJob(1);
			model.addAttribute("recent_apply_jobs", recent_apply_jobs);

		} else {
			System.out.println("Not Login: ");
			
		}

		return "admin/dashboard";
	}

}
