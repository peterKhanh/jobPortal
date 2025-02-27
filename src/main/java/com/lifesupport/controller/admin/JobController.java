package com.lifesupport.controller.admin;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lifesupport.ConstantsClass;
import com.lifesupport.models.Enterprise;
import com.lifesupport.models.Job;
import com.lifesupport.models.JobCategory;
import com.lifesupport.models.Location;
import com.lifesupport.models.WorkingModel;
import com.lifesupport.repository.EnterpriseRepository;
import com.lifesupport.repository.JobCategoryRepository;
import com.lifesupport.repository.JobRepository;
import com.lifesupport.repository.LocationRepository;
import com.lifesupport.repository.WorkingModelRepository;
import com.lifesupport.service.FilesStorageService;
import com.lifesupport.service.JobService;
import com.lifesupport.service.UserService;

@Controller
@RequestMapping("/admin/job")
public class JobController {
	@Autowired
	private JobRepository jobRepo;
	@Autowired
	private EnterpriseRepository enterpriseRepo;
	
	@Autowired
	private JobCategoryRepository jobCategoryRepo;
	@Autowired
	private LocationRepository locationRepo;
	
	@Autowired
	private WorkingModelRepository workingModelRepo;
	
	@Autowired
	private JobService jobService;

	@Autowired
	private UserService userService;

	@Autowired
	FilesStorageService storageService;

	
	@GetMapping("/list")
	public String getAllJobSearchAndPaging(Model model, Principal principal, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		userService.checkLogin(model, principal);

		getAllList(model);

		
		Page<Job> jobs = jobService.getAll(pageNo);
		if (keyword != null) {
			System.out.println("keyword : " + keyword);
			jobs = jobService.searchJob(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		
		model.addAttribute("totalPage", jobs.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		System.out.println("keyword : " + jobs.getTotalPages());

		model.addAttribute("jobs", jobs);
		return "admin/jobs/jobs";
	}
/*
 * Show form add new job
 * */
	@GetMapping("/add")
	public String showAddPage(Model model, Principal principal) {
		String app_name = ConstantsClass.APPLICATION_NAME;
		userService.checkLogin(model, principal);

		getAllList(model);
		
		return "admin/jobs/add";
	}

	@GetMapping("/add-job")
	public String showAddNewPage(Model model, @RequestParam Long enterprise_id, Principal principal) {
		String app_name = ConstantsClass.APPLICATION_NAME;
		userService.checkLogin(model, principal);

		System.out.println(app_name + " --- " + enterprise_id.toString());
		Enterprise active_enterprise = enterpriseRepo.findById(enterprise_id).get();

//		System.out.println(enterprise.getName().toString());
		model.addAttribute("active_enterprise", active_enterprise);

		
//		tantsClass.APPLICATION_NAME;
		getAllList(model);
		
		return "admin/jobs/addJob";
	}


	@PostMapping("/add-job")
	public String addEnterprise(Model model, @RequestParam Long enterprise_id, @ModelAttribute Job form) {
		Date createAt = new Date();
		Enterprise enterprise = enterpriseRepo.findById(enterprise_id).get();
		System.out.println("enterprise_id: "+ enterprise_id.toString());

		Job job = new Job();
//		job.setEnterprise(form.getEnterprise());
		job.setEnterprise(enterprise);
		job.setCreateAt(createAt);
		job.setTitle(form.getTitle());
		job.setJobCategory(form.getJobCategory());
		job.setWorkingModel(form.getWorkingModel());
		job.setNumberOfRecruitement(form.getNumberOfRecruitement());
		job.setYearOfExperience(form.getYearOfExperience());
		job.setTrialTime(form.getTrialTime());
		job.setWorkingTime(form.getWorkingTime());
		job.setSalary(form.getSalary());
		job.setLocation(form.getLocation());
		job.setWorkingAddress(form.getWorkingAddress());
		job.setGender(form.getGender());
		job.setAgeRange(form.getAgeRange());
		job.setExpiredDate(form.getExpiredDate());
		job.setReponsibility(form.getReponsibility());
		job.setDescription(form.getDescription());
		job.setBenefit(form.getBenefit());
		job.setStatus(form.getStatus());
		jobRepo.save(job);

		return "redirect:list";
	}
	

	@PostMapping("/add")
	public String addEnterprised(Model model, @ModelAttribute Job form) {
		Date createAt = new Date();

		Job job = new Job();
		job.setEnterprise(form.getEnterprise());
//		job.setEnterprise(enterprise);
		job.setCreateAt(createAt);
		job.setTitle(form.getTitle());
		job.setJobCategory(form.getJobCategory());
		job.setWorkingModel(form.getWorkingModel());
		job.setNumberOfRecruitement(form.getNumberOfRecruitement());
		job.setYearOfExperience(form.getYearOfExperience());
		job.setTrialTime(form.getTrialTime());
		job.setWorkingTime(form.getWorkingTime());
		job.setSalary(form.getSalary());
		job.setLocation(form.getLocation());
		job.setWorkingAddress(form.getWorkingAddress());
		job.setGender(form.getGender());
		job.setAgeRange(form.getAgeRange());
		job.setExpiredDate(form.getExpiredDate());
		job.setReponsibility(form.getReponsibility());
		job.setDescription(form.getDescription());
		job.setBenefit(form.getBenefit());
		job.setStatus(form.getStatus());
		jobRepo.save(job);

		return "redirect:list";
	}
//	View form Update Job

	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam Long id, Principal principal) {
		userService.checkLogin(model, principal);

		getAllList(model);
		Job job = jobRepo.findById(id).get();
		model.addAttribute("job", job);
		return "admin/jobs/edit";
	}
	

	@PostMapping("/edit")
	public String editJob(Model model, @RequestParam Long id, @ModelAttribute Job form) {
		Job curent_job = jobRepo.findById(id).get();
		model.addAttribute(curent_job);
		Date updateAt = new Date();

		curent_job.setEnterprise(form.getEnterprise());
		curent_job.setUpdateAt(updateAt);
		curent_job.setTitle(form.getTitle());
		curent_job.setJobCategory(form.getJobCategory());
		
		curent_job.setWorkingModel(form.getWorkingModel());
		curent_job.setNumberOfRecruitement(form.getNumberOfRecruitement());
		curent_job.setYearOfExperience(form.getYearOfExperience());
		curent_job.setTrialTime(form.getTrialTime());
		curent_job.setWorkingTime(form.getWorkingTime());
		curent_job.setSalary(form.getSalary());
		curent_job.setLocation(form.getLocation());
		
		curent_job.setWorkingAddress(form.getWorkingAddress());
		curent_job.setGender(form.getGender());
		curent_job.setAgeRange(form.getAgeRange());
		curent_job.setExpiredDate(form.getExpiredDate());
		curent_job.setReponsibility(form.getReponsibility());
		curent_job.setDescription(form.getDescription());
		curent_job.setBenefit(form.getBenefit());
		curent_job.setStatus(form.getStatus());
		jobRepo.save(curent_job);
		return "redirect:list";

	}
	

	
/***
 * Create by: Peter
 * Create date: 12/2024
 **/	
	@GetMapping("/delete")
	public String deleteJob(@RequestParam Long id) {
		Job job = jobRepo.findById(id).get();
	
	  	try {
		// delete product
	  		jobRepo.delete(job);
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
		return "redirect:list";

	}

	/*
	 * Get all list to from database to generate in form
	 * Create: 12/12/2024
	 * Create by: Peter
	 */
	
	public void getAllList(Model model) {
		List<Enterprise> enterprises = enterpriseRepo.getAllEnterprise();
		model.addAttribute("enterprises", enterprises);
//		List<Category> categories = categoryRepo.findAllByActive();
//		model.addAttribute("categories", categories);
		List<JobCategory> jobcategories = jobCategoryRepo.findAllByActive();
		model.addAttribute("jobcategories", jobcategories);
	
		List<Location> locations = locationRepo.findAllByActive();
		model.addAttribute("locations", locations);
		List<WorkingModel> workingModels = workingModelRepo.findAllByActive();
		model.addAttribute("workingModels", workingModels);
		model.addAttribute("location", workingModels);
	}

}
