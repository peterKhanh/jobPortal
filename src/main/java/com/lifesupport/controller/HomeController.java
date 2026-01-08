package com.lifesupport.controller;

import java.nio.file.FileSystem;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifesupport.models.Blog;
import com.lifesupport.models.Category;
import com.lifesupport.models.Enterprise;
import com.lifesupport.models.Job;
import com.lifesupport.models.JobCategory;
import com.lifesupport.models.Location;
import com.lifesupport.models.MailInfo;
import com.lifesupport.models.Slide;
import com.lifesupport.models.Tastimonial;
import com.lifesupport.repository.BlogRepository;
import com.lifesupport.repository.CategoryRepository;
import com.lifesupport.repository.EnterpriseRepository;
import com.lifesupport.repository.JobCategoryRepository;
import com.lifesupport.repository.LocationRepository;
import com.lifesupport.repository.SlideRepository;
import com.lifesupport.repository.TastimonialRepository;
import com.lifesupport.service.CategoryService;
import com.lifesupport.service.JobService;
import com.lifesupport.service.MailService;
import com.lifesupport.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private EnterpriseRepository enterpriseRepo;

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private JobCategoryRepository jobCateRepo;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private LocationRepository locationRepo;
	@Autowired
	private TastimonialRepository tastimonialRepository;
	@Autowired
	private SlideRepository slideRepository;
	@Autowired
	private CategoryRepository cateRepository;
	@Autowired
	private UserService userService;
	
	@Autowired
	private JobService jobService;
	@Autowired
	MailService mailService;
	
	
	@GetMapping("/")
	public String home(Model model, Principal principal, 
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		userService.checkLogin(model, principal);
//		Get Enterprise 
		List<Enterprise> listEnterprise = enterpriseRepo.getAllEnterpriseForHomePage()	;
		model.addAttribute("listEnterprise", listEnterprise);
		System.out.println("Total listEnterprise: "+ listEnterprise.size());

		List<JobCategory> lists = jobCateRepo.findAllPopularCate()	;
		model.addAttribute("lists", lists);
//		System.out.println("Total category: "+ lists.size());

		List<Location> locations = locationRepo.findAllByActive();
		model.addAttribute("locations", locations);

		
		// Page<Job> jobs = jobService.getAll(pageNo);
		Page<Job> jobs = jobService.getAllJobForHomePage(pageNo);
		model.addAttribute("totalPage", jobs.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		System.out.println("totalPage : " + jobs.getTotalPages());
		model.addAttribute("jobs", jobs);

		Category cate = cateRepository.findById(2).get();
	
		List<Blog> blogOnHomePage1 = blogRepository.findTop4ByCategory(cate);
		
		List<Blog> blogOnHomePage = blogOnHomePage1.subList(0, 4);
		System.out.println("blogOnHomePage:" + blogOnHomePage.size());
		
		model.addAttribute("blogOnHomePage", blogOnHomePage);

		
		List<Tastimonial> listTastimonial = tastimonialRepository.findByStatusTrue();
		model.addAttribute("listTastimonial", listTastimonial);

		return "index";
	}

	@RequestMapping("/logon")
	public String login() {
		return "admin/logon";
	}

	 @RequestMapping(value= {"/default"}, method = RequestMethod.GET)
	    public String defaultAfterLogin() {
	        Collection<? extends GrantedAuthority> authorities;
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        authorities = auth.getAuthorities();
	        String myRole = authorities.toArray()[0].toString();
	        String admin = "ADMIN";
	        if (myRole.equals(admin)) {
	            return "redirect:/admin";
	        }else if (myRole.equals("USER")) {
	            return "redirect:/employer/";
	        }else if (myRole.equals("CANDIDATE")) {
	            return "redirect:/candidate/";
	        } 
	        return "redirect:/";
	    }
 


	@RequestMapping("/contact-form")
	public String view_contact_form() {
		return "layout/gallary/contact";
	}
	
	
	@ResponseBody
	@GetMapping("/contact")
	public boolean sendToFriend(Model model) {
		//Send mail to friend
		String from = "tkhn2020@gmail.com";
		String email = "tkhn2020@gmail.com";
		String comment = "<p>Hello,</p><p>This is a test email sent from Spring Boot.</p>";
		comment = comment + " <a href='https://pabasararathnayake.medium.com/interfacing-4x4-matrix-keypad-with-pic16f877a-using-mm74c922-encoder-42e7809e797a'>Click Here to Xác nhận</a>";
	    String subject = "Hello from Spring Boot";
	    String file = "D:\\LS Group\\Virtual_Sensors.pdf";
	  
		MailInfo info = new MailInfo(from, email, null,null,subject, comment, file);
		try {
			mailService.send(info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
