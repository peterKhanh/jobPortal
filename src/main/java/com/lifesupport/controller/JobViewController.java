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

import com.lifesupport.models.Enterprise;
import com.lifesupport.models.Job;
import com.lifesupport.models.JobCategory;
import com.lifesupport.models.Location;
import com.lifesupport.repository.BlogTagRepository;
import com.lifesupport.repository.CommentRepository;
import com.lifesupport.repository.EnterpriseRepository;
import com.lifesupport.repository.JobCategoryRepository;
import com.lifesupport.repository.JobRepository;
import com.lifesupport.repository.LocationRepository;
import com.lifesupport.service.CategoryService;
import com.lifesupport.service.JobService;
import com.lifesupport.service.UserService;

@Controller
@RequestMapping("/job")
public class JobViewController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private EnterpriseRepository enterpriseRepo;
	@Autowired
	private JobRepository jobRepo;

	@Autowired
	private JobCategoryRepository jobCateRepo;
	@Autowired
	private CommentRepository commentRepo;
	@Autowired
	private BlogTagRepository blogTagRepo;
	@Autowired
	private LocationRepository locationRepo;

	@Autowired
	private JobService jobService;

	@Autowired
	private UserService userService;

	@GetMapping("")
	public String getAllJobSearchAndPaging(Model model, @Param("keyword") String keyword,
			@Param("location") Location location, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			Principal principal) {

		userService.checkLogin(model, principal);

		List<Location> locations = locationRepo.findAllByActive();
		model.addAttribute("locations", locations);

		Page<Job> jobs = jobService.getAll(pageNo);

		if (keyword != null) {
			jobs = jobService.searchJob(keyword, pageNo);
			model.addAttribute("keyword", keyword);
			System.out.println("keyword:" + keyword);
		}

//		if (location != null) {
//			System.out.println("Location search:" + location.getName());
//			model.addAttribute("location", location);
//
//			
//		}
		model.addAttribute("totalPage", jobs.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		System.out.println("Total page by search:" + jobs.getTotalPages());

		model.addAttribute("jobs", jobs);

		List<Job> recentjobs = jobRepo.findTop10ByOrderByCreateAtDesc();
		model.addAttribute("recentjobs", recentjobs);

		List<JobCategory> listsCate123 = jobCateRepo.findAllPopularCate();
		model.addAttribute("listsCate123", listsCate123);

		return "views/job/view-job-search";
	}


	@GetMapping("/view-cate-search")
	public String getAllJobCate(Model model, @Param("keyword") String keyword,
			@Param("location") Location location, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			Principal principal) {
		getAllList(model);
		userService.checkLogin(model, principal);
		Page<Job> jobs = jobService.getAll(pageNo);

		if (keyword != null) {
			jobs = jobService.searchJob(keyword, pageNo);
			model.addAttribute("keyword", keyword);
			System.out.println("keyword:" + keyword);
		}

//		if (location != null) {
//			System.out.println("Location search:" + location.getName());
//			model.addAttribute("location", location);
//
//			
//		}
		model.addAttribute("totalPage", jobs.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		System.out.println("Total page by search:" + jobs.getTotalPages());

		model.addAttribute("jobs", jobs);

		List<Job> recentjobs = jobRepo.findTop10ByOrderByCreateAtDesc();
		model.addAttribute("recentjobs", recentjobs);

		List<JobCategory> listsCate123 = jobCateRepo.findAllPopularCate();
		model.addAttribute("listsCate123", listsCate123);

		return "views/job/view-cate-search";
	}


//	Get company in formation
//	Get all Opening Job of Company
	@GetMapping("/viewEnterprise/{id}")
	public String shopGetJobByCate(Model model, @PathVariable("id") Long id,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, Principal principal) {
		userService.checkLogin(model, principal);

		Enterprise enterprise = enterpriseRepo.findById(id).get();
		model.addAttribute("enterprise", enterprise);

		List<Job> jobs = jobService.GetAllActiveJobByEnterprise(enterprise);
		model.addAttribute("jobs", jobs);
		List<Job> recentjobs = jobRepo.findTop10ByOrderByCreateAtDesc();
		model.addAttribute("recentjobs", recentjobs);

		List<JobCategory> listsCate123 = jobCateRepo.findAllPopularCate();
		model.addAttribute("listsCate123", listsCate123);

		return "views/job/view-enterprise";
	}

	@GetMapping("/jobbycate/{id}")
	public String getApprovedJobByCate(Model model, @PathVariable("id") Integer id,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, Principal principal) {
		userService.checkLogin(model, principal);
				getAllList(model);

		JobCategory jobCategory = jobCateRepo.findById(id).get();
		model.addAttribute("jobCategory", jobCategory);

		Page<Job> jobs = jobService.getApprovedJobByCategory(pageNo, id);

		model.addAttribute("totalPage", jobs.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		System.out.println("keyword : " + jobs.getTotalPages());

		model.addAttribute("jobs", jobs);
		List<Job> recentjobs = jobRepo.findTop10ByOrderByCreateAtDesc();
		model.addAttribute("recentjobs", recentjobs);

		return "views/job/viewJobCate";
	}

	@GetMapping("/jobbylocation/{id}")
	public String getApprovedJobByLocation(Model model, @PathVariable("id") Integer id,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, Principal principal) {
		userService.checkLogin(model, principal);
	
		Location location = locationRepo.findById(id).get();
		model.addAttribute("location", location);

		Page<Job> jobs = jobService.getApprovedJobByLocation(pageNo, id);

		model.addAttribute("totalPage", jobs.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		System.out.println("keyword : " + jobs.getTotalPages());

		model.addAttribute("jobs", jobs);
		List<Job> recentjobs = jobRepo.findTop10ByOrderByCreateAtDesc();
		model.addAttribute("recentjobs", recentjobs);

		return "views/job/view-job-by-location";
	}

	
	
	
	
//	Get Job detail
//	
	@GetMapping("/viewjob/{id}")
	public String GetJobDetail(Model model, @PathVariable("id") Long id, Principal principal) {

		userService.checkLogin(model, principal);

		Job job = jobService.getSingleJob(id).get();
		model.addAttribute("job", job);

		// Update viewcount
		int v_viewcount = 0;
		if (job.getViewCount() != null) {
			v_viewcount = job.getViewCount() + 1;
		} else {
			v_viewcount = 1;
		}
		job.setViewCount(v_viewcount);
		jobRepo.save(job);

		List<Job> relatejobs = jobRepo.findTop10ByJobCategoryOrderByCreateAtDesc(job.getJobCategory());
		model.addAttribute("relatejobs", relatejobs);

		Integer cate_id = job.getJobCategory().getId();

		List<JobCategory> listsCate123 = jobCateRepo.findAllPopularCate();
		model.addAttribute("listsCate123", listsCate123);

		return "views/job/view-job";
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

		List<Location> locations = locationRepo.findAllByActive();
		model.addAttribute("locations", locations);
//		List<WorkingModel> workingModels = workingModelRepo.findAllByActive();
//		model.addAttribute("workingModels", workingModels);
	}

}
