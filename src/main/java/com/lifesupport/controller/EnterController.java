package com.lifesupport.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lifesupport.models.Blog;
import com.lifesupport.models.BlogCate;
import com.lifesupport.models.Enterprise;
import com.lifesupport.models.Job;
import com.lifesupport.models.JobCategory;
import com.lifesupport.models.Location;
import com.lifesupport.models.Tastimonial;
import com.lifesupport.repository.BlogTagRepository;
import com.lifesupport.repository.CommentRepository;
import com.lifesupport.repository.EnterpriseRepository;
import com.lifesupport.repository.IndustrialTypeRepository;
import com.lifesupport.repository.JobCategoryRepository;
import com.lifesupport.repository.JobRepository;
import com.lifesupport.repository.LocationRepository;
import com.lifesupport.service.CategoryService;
import com.lifesupport.service.EnterService;
import com.lifesupport.service.UserService;

@Controller
@RequestMapping("/enter")
public class EnterController {
	@Autowired
	private EnterpriseRepository enterpriseRepo;
	@Autowired
	private EnterService  enterpriseService;

	@Autowired
	private JobCategoryRepository jobCateRepo;
	@Autowired
	private IndustrialTypeRepository industrialTypeRepository;

	@Autowired
	private UserService userService;

	@GetMapping("")
	public String home(Model model, Principal principal, 
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		userService.checkLogin(model, principal);
	
		//		Get Enterprise 
	//	List<Enterprise> listEnterprise = enterpriseRepo.getAllEnterpriseForHomePage()	;
	//	model.addAttribute("listEnterprise", listEnterprise);
	//	System.out.println("Total listEnterprise: "+ listEnterprise.size());

		Page<Enterprise> listEnterprise =  enterpriseService.getEnterpriseList(pageNo);
		model.addAttribute("totalPage", listEnterprise.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		System.out.println("totalPage : " + listEnterprise.getTotalPages());
		model.addAttribute("listEnterprise", listEnterprise);

		return "views/enterprise/index";
	}

	public void getAllList(Model model) {
//		List<Enterprise> enterprises = enterpriseRepo.getAllEnterprise();
//		model.addAttribute("enterprises", enterprises);
//		List<Category> categories = categoryRepo.findAllByActive();
//		model.addAttribute("categories", categories);
//		List<JobCategory> jobcategories = jobCategoryRepo.findAllByActive();
//		model.addAttribute("jobcategories", jobcategories);
//	


		List<JobCategory> lists = jobCateRepo.findAllPopularCate()	;
		model.addAttribute("lists", lists);

		//List<Location> locations = locationRepo.findAllByActive();
		//model.addAttribute("locations", locations);
//		List<WorkingModel> workingModels = workingModelRepo.findAllByActive();
//		model.addAttribute("workingModels", workingModels);
	}

}
