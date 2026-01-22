package com.lifesupport.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lifesupport.ConstantsClass;
import com.lifesupport.models.MailInfo;
import com.lifesupport.models.Enterprise;
import com.lifesupport.models.IndustrialType;
import com.lifesupport.models.Job;
import com.lifesupport.models.JobCategory;
import com.lifesupport.models.Location;
import com.lifesupport.models.Profile;
import com.lifesupport.models.Role;
import com.lifesupport.models.User;
import com.lifesupport.models.UserRole;
import com.lifesupport.models.WorkingModel;
import com.lifesupport.repository.EnterpriseRepository;
import com.lifesupport.repository.JobApplyRepository;
import com.lifesupport.repository.JobCategoryRepository;
import com.lifesupport.repository.JobRepository;
import com.lifesupport.repository.LocationRepository;
import com.lifesupport.repository.ProfileRepository;
import com.lifesupport.repository.RoleRepository;
import com.lifesupport.repository.UserRepository;
import com.lifesupport.repository.UserRoleRepository;
import com.lifesupport.repository.WorkingModelRepository;
import com.lifesupport.service.FilesStorageService;
import com.lifesupport.service.IndustrialTypeService;
import com.lifesupport.service.JobService;
import com.lifesupport.service.UserService;
import com.lifesupport.service.MailService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/employer")
public class EmployerController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository repoUser;

	@Autowired
	private ProfileRepository repoProfile;

	@Autowired
	private EnterpriseRepository enterpriseRepo;

	@Autowired
	private JobCategoryRepository jobCategoryRepo;
	@Autowired
	private LocationRepository locationRepo;

	@Autowired
	private WorkingModelRepository workingModelRepo;

	@Autowired
	private JobApplyRepository jobApplyRepo;

	@Autowired
	private JobRepository jobRepo;

	@Autowired
	private JobService  jobService;

	@Autowired
	private RoleRepository repoRole;
	@Autowired
	private IndustrialTypeService industrialTypeService;

	@Autowired
	private UserRoleRepository repoUserRole;

	@Autowired
	FilesStorageService storageService;
	
	@Autowired
	MailService mailService;

	
	// Lấy giá trị CONST_USER_IMAGE_UPLOAD_PATH
	String upload_path = ConstantsClass.CONST_USER_IMAGE_UPLOAD_PATH;

	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/register")
	public String addNewUser(Model model) {
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		return "views/employer/register";
	}

	@PostMapping("/register")
	public String register(Model model, @ModelAttribute User form_data) {
		String roleName = "USER";
//		Role role = repoRole.findByName(roleName);
		// System.out.println("ID: " + id);
		Date createAt = new Date();
//		System.out.println(user.toString());
		System.out.println("Time create: " + createAt);
		System.out.println("User name: " + form_data.getUserName());
		System.out.println("Pasword: " + form_data.getPassWord());

		User user = new User();
		user.setUserName(form_data.getUserName());
		user.setFullName(form_data.getFullName());
		user.setAddress(form_data.getAddress());
		user.setEmail(form_data.getEmail());
		user.setUserName(form_data.getUserName());
		user.setPassWord(bCryptPasswordEncoder.encode(form_data.getPassWord()));

		user.setEnabled(true);

		if (this.userService.create(user)) {
			UserRole userRole = new UserRole();
			Role role = repoRole.findByName(roleName);
			userRole.setUser(user);
			userRole.setRole(role);
			repoUserRole.save(userRole);
		}
		return "redirect:/logon";

	}

		
//	Hiển thị màn hình sửa thông tin User
	@GetMapping("/edit-user/")
	public String viewEditUser(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("user", loggedUser);

		} else {
			return "redirect:/logon/";
		}
		return "views/employer/edit-account";
	}

	@PostMapping("/edituser")
	public String updateUserProfile(@ModelAttribute User user_form, @RequestParam("file_image") MultipartFile image) {
		System.out.println("User n: " + user_form.getFullName());
		Long user_id = user_form.getId();
		System.out.println("User ID : " + user_id);

		User updateUser = repoUser.findById(user_id).get();

		updateUser.setFullName(user_form.getFullName());
		updateUser.setEmail(user_form.getEmail());
		updateUser.setAddress(user_form.getAddress());
		updateUser.setTelephone(user_form.getTelephone());
		updateUser.setGender(user_form.getGender());

		Date curentDate = new Date();

		// if (!gala.getImageFileName().isBlank()) {
		if (!image.getOriginalFilename().isEmpty()) {
			// Xoa File
			Path imagePath = Paths.get(upload_path + updateUser.getAvata());
			
			try {
				if (updateUser.getAvata() != null) {
					System.out.println("Xoa File cũ " + updateUser.getAvata());
					Files.delete(imagePath);
					System.out.println("Xoa File thanh cong: ");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			String storageFileName = curentDate.getTime() + "_" + image.getOriginalFilename();
			storageService.saveFile(upload_path, image, storageFileName);
			updateUser.setAvata(storageFileName);
		} else {
			System.out.println("Khong sua File: ");
		}

		repoUser.save(updateUser);

		return "redirect:/employer/account/";
	}
	
	
	
	
	
// Kierm tra ten dang nhap
	// Neu da ton tai => False
	
	@ResponseBody
	@GetMapping("/checkUsername")
	public Boolean checkUsername(Model model, @RequestParam("userName") String userName ) {
		System.out.println("User name: " + userName);
		User user_check = repoUser.findByUserName(userName);

		if (user_check != null) {
			return false;

		} else {
			return true;
		}
		
		
	}

	/*
	 * Kiểm tra Email khi đăng ký User
	 * Nếu Email đã tồn tại => trả về True => Thông báo Email đã tồn tại
	 */	
	@ResponseBody
	@GetMapping("/checkemail")
	public Boolean CheckUserEmail(Model model, @RequestParam("email") String email) {
		System.out.println("usernameValue : " + email);
		User user_check = repoUser.findTop1ByEmail(email);

		if (user_check != null) {
			return false;
		} else {
			return true;
		}	
	}

	
	// Check password
	// Neu Pass dung => true
	@ResponseBody
	@GetMapping("/checkPassword")
	public Boolean checkCurentPassword(Model model, @RequestParam("passWord") String passWord, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			String curent_password = loggedUser.getPassWord();
			String pass_input_encode = bCryptPasswordEncoder.encode(passWord);
			Boolean check_pass = BCrypt.checkpw(passWord, curent_password);
			return check_pass;
		} else {
			return false;
		}
	}

	@GetMapping("/change-pasword/")
	public String changepasword(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("user", loggedUser);
		} else {
			return "redirect:/logon/";

		}
		return "views/employer/change-password";
	}

	@PostMapping("/change-pasword")
	public String changepaswords(@ModelAttribute User user_form, @RequestParam("newPassWord") String newPassWord) {

		Long user_id = user_form.getId();
		System.out.println("User ID : " + user_id);
		User logUser = repoUser.findById(user_id).get();
		String pass_input_encode = bCryptPasswordEncoder.encode(newPassWord);
		logUser.setPassWord(pass_input_encode);
		repoUser.save(logUser);		
		return "redirect:/employer/";
	}

	@GetMapping("/")
	public String dashboad(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		getAllList(model);

		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("loggedUser", loggedUser);
		 	loggedUser.getUserRoles();
			
			
			return "views/employer/dashboard";
		}else {
			return "redirect:/logon/";
		}
	}

	

	@GetMapping("/account/")
	public String viewAccount(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		getAllList(model);

		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("loggedUser", loggedUser);

			List<Profile> profiles = repoProfile.findAllByUser(loggedUser);
			model.addAttribute("profiles", profiles);
			model.addAttribute("pro", profiles.size());
			System.out.println("Profile: " + profiles);
			return "views/employer/index";
		}else {
			return "redirect:/logon/";
		}
	}


		
	@GetMapping("/viewemployer/")
	public String viewEmployer(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		getAllList(model);

		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("loggedUser", loggedUser);

			Enterprise enterprise = enterpriseRepo.findByUser(loggedUser);
			List<IndustrialType> industrialType = industrialTypeService.getAllActiveIndustrialType();
			model.addAttribute("industrialType", industrialType);
			
			 if (enterprise == null) {
				model.addAttribute("employer", "NOTSET");
			 }else {
				model.addAttribute("user", loggedUser);
				model.addAttribute("employer", enterprise);
			 }

			 return "views/employer/viewEmployer";
		}else {
			return "redirect:/logon/";
		}
	}
	
//	Hiển thị màn hình them cong ty
	@GetMapping("/add-employer/")
	public String viewAddEmployer(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			
			Enterprise enterprise = enterpriseRepo.findByUser(loggedUser);

			List<IndustrialType> industrialType = industrialTypeService.getAllActiveIndustrialType();
			model.addAttribute("industrialType", industrialType);
	
			 if (enterprise == null) {
				 System.out.println("enterprise:sdsdfsd "); 
				model.addAttribute("employer", "NOTSET");
			 }else {
				 System.out.println("enterprise:OK "); 		 
				model.addAttribute("user", loggedUser);
				model.addAttribute("employer", enterprise);
			 }
		} else {
			return "redirect:/logon/";
		}
		return "views/employer/add-employer";
	}
	
	
	

	



	@PostMapping("/add-employer")
	public String addEmployer(Principal principal, @ModelAttribute Enterprise form) {
		Date createAt = new Date();
		Boolean status = true;
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);

//			System.out.println(form.getName());
//			System.out.println(form.getAddress());
//			System.out.println(form.getIntroduction());
//			System.out.println(form.getNumberOfEmployee());
//			System.out.println(form.getWebsite());
//			System.out.println(form.getIndustrialType());
			Enterprise enterprise = new Enterprise();
			
			enterprise.setName(form.getName());
			enterprise.setAddress(form.getAddress());
			enterprise.setCreateAt(createAt);
			enterprise.setIndustrialType(form.getIndustrialType());
			enterprise.setIntroduction(form.getIntroduction());
			enterprise.setNumberOfEmployee(form.getNumberOfEmployee());
			enterprise.setWebsite(form.getWebsite());
			enterprise.setUser(loggedUser);
			enterpriseRepo.save(enterprise);
			

		} else {
			return "redirect:/logon/";

		}

		return "redirect:/employer/viewemployer/";
	}

//	Hiển thị màn hình sửa thông tin cong ty
	@GetMapping("/edit-employer/")
	public String viewEditEmployer(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			
			Enterprise enterprise = enterpriseRepo.findByUser(loggedUser);
			List<IndustrialType> industrialType = industrialTypeService.getAllActiveIndustrialType();
			model.addAttribute("industrialType", industrialType);
						
			 if (enterprise == null) {
				 System.out.println("enterprise:sdsdfsd "); 
			 }else {
				System.out.println("enterprise:OK "); 		 
				model.addAttribute("user", loggedUser);
				model.addAttribute("employer", enterprise);
			 }
		} else {
			return "redirect:/logon/";
		}
		return "views/employer/edit-employer";
	}
	
	
	
		

	@PostMapping("/edit-employer")
	public String editEmployer(Principal principal,@RequestParam Long id, @ModelAttribute Enterprise form) {
		Date updateAt = new Date();
		Boolean status = true;
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			
			Enterprise enterprise = enterpriseRepo.findById(id).get();
			
			System.out.println(form.getName());
			System.out.println(form.getAddress());
			System.out.println(form.getIntroduction());
			System.out.println(form.getNumberOfEmployee());
			System.out.println(form.getWebsite());
			System.out.println(form.getIndustrialType());
	//		Enterprise enterprise = new Enterprise();
			
			enterprise.setName(form.getName());
			enterprise.setAddress(form.getAddress());
			enterprise.setUpdateAt(updateAt);
			enterprise.setIndustrialType(form.getIndustrialType());
			enterprise.setIntroduction(form.getIntroduction());
			enterprise.setNumberOfEmployee(form.getNumberOfEmployee());
			enterprise.setWebsite(form.getWebsite());
			enterpriseRepo.save(enterprise);
			


		} else {
			return "redirect:/logon/";

		}

		return "redirect:/employer/viewemployer/";
	}


	
	
	
//	Hiển thị màn hình them cong viec
	@GetMapping("/add-job/")
	public String viewAddJob(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		getAllList(model);

		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			
			Enterprise enterprise = enterpriseRepo.findByUser(loggedUser);

			List<IndustrialType> industrialType = industrialTypeService.getAllActiveIndustrialType();
			model.addAttribute("industrialType", industrialType);
	
			 if (enterprise == null) {
				model.addAttribute("employer", "NOTSET");
			 }else {
				System.out.println("enterprise:OK "); 		 
				model.addAttribute("user", loggedUser);
				model.addAttribute("employer", enterprise);
			 }
		} else {
			return "redirect:/logon/";
		}
		return "views/employer/add-job";
	}
	
	
	@PostMapping("/add-job")
	public String addJob(Model model, @RequestParam Long employer_id, @ModelAttribute Job form) {
		Date createAt = new Date();
		Enterprise enterprise = enterpriseRepo.findById(employer_id).get();
		System.out.println("employer_id: "+ employer_id.toString());

		
		Job job = new Job(); 
		job.setEnterprise(enterprise);
		job.setCreateAt(createAt); job.setTitle(form.getTitle());
		job.setJobCategory(form.getJobCategory());
		job.setWorkingModel(form.getWorkingModel());
		job.setNumberOfRecruitement(form.getNumberOfRecruitement());
		job.setYearOfExperience(form.getYearOfExperience());
		job.setTrialTime(form.getTrialTime());
		job.setWorkingTime(form.getWorkingTime()); job.setSalary(form.getSalary());
		job.setLocation(form.getLocation());
		job.setWorkingAddress(form.getWorkingAddress());
		job.setWorkingAddress(form.getWorkingAddress());
		
		job.setGender(form.getGender()); job.setAgeRange(form.getAgeRange());
		job.setExpiredDate(form.getExpiredDate());
		job.setReponsibility(form.getReponsibility());
		job.setDescription(form.getDescription()); job.setBenefit(form.getBenefit());
		job.setStatus(form.getStatus()); jobRepo.save(job);
		return "redirect:job-by-employer/"; 
		
	}
		
//	Hiển thị màn hình them cong viec
	@GetMapping("/edit-job/{id}")
	public String vieweditJob(Model model, @PathVariable("id") Long id, Principal principal) {
		userService.checkLogin(model, principal);
		getAllList(model);
		System.out.println("enterprise:OK " + id.toString()); 		 

		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			
			Job job = jobService.getSingleJob(id).get();
			model.addAttribute("job", job);
		

		} else {
			return "redirect:/logon/";
		}
		return "views/employer/edit-job";
	}

	

	@PostMapping("/edit-job")
	public String editJob(Principal principal,@RequestParam Long id, @ModelAttribute Job form) {
		Date updateAt = new Date();
		Boolean status = true;
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			
			
			Job job = jobRepo.findById(id).get();
		
			System.out.println(form.getTitle());
			System.out.println(form.getAddress());
			System.out.println(form.getDescription());
			System.out.println(form.getReponsibility());
		
				job.setUpdateAt(updateAt);
				 job.setTitle(form.getTitle());
		job.setJobCategory(form.getJobCategory());
		job.setWorkingModel(form.getWorkingModel());
		job.setNumberOfRecruitement(form.getNumberOfRecruitement());
		job.setYearOfExperience(form.getYearOfExperience());
		job.setTrialTime(form.getTrialTime());
		job.setWorkingTime(form.getWorkingTime()); job.setSalary(form.getSalary());
		job.setLocation(form.getLocation());
		job.setWorkingAddress(form.getWorkingAddress());
		
		job.setGender(form.getGender()); job.setAgeRange(form.getAgeRange());
		job.setExpiredDate(form.getExpiredDate());
		job.setReponsibility(form.getReponsibility());
		job.setDescription(form.getDescription()); job.setBenefit(form.getBenefit());
		job.setStatus(form.getStatus()); jobRepo.save(job);
	
		return "redirect:job-by-employer/"; 
		} else {
			return "redirect:/logon/";

		}

	}	
		

		
	
	@GetMapping("/job-by-employer/")
	public String viewJobByEmployer(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			Enterprise enterprise = enterpriseRepo.findByUser(loggedUser);
			model.addAttribute("user", loggedUser); //GetAllActiveJobByEnterprise
			List<Job> jobApplyByEmployer = jobService.GetAllActiveJobByEnterprise(enterprise);
			model.addAttribute("jobApplyByEmployer", jobApplyByEmployer);

		} else {
			return "redirect:/logon/";
		}
		return "views/employer/job-by-employer";
	}

	@GetMapping("/editjob-by-employer/")
	public String viewEditingJobByEmployer(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			Enterprise enterprise = enterpriseRepo.findByUser(loggedUser);
			model.addAttribute("user", loggedUser); //GetAllActiveJobByEnterprise
			List<Job> jobEditByEmployer = jobService.GetAllJobOfEnterpriseByStatus(enterprise, "EDITING");
			model.addAttribute("jobEditByEmployer", jobEditByEmployer);
			System.out.println("jobEditByEmployer: " + jobEditByEmployer.size());
		} else {
			return "redirect:/logon/";
		}
		return "views/employer/job-editing-by-employer";
	}

	@GetMapping("/blockedjob-by-employer/")
	public String viewBlockedJobByEmployer(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			Enterprise enterprise = enterpriseRepo.findByUser(loggedUser);
			model.addAttribute("user", loggedUser); 
			List<Job> jobBlockedByEmployer = jobService.GetAllJobOfEnterpriseByStatus(enterprise, "BLOCKED");
			model.addAttribute("jobEditByEmployer", jobBlockedByEmployer);
			System.out.println("jobEditByEmployer: " + jobBlockedByEmployer.size());
		} else {
			return "redirect:/logon/";
		}
		return "views/employer/job-block-by-employer";
	}


	@GetMapping("/expiredjob-by-employer/")
	public String viewExpiredJobByEmployer(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			Enterprise enterprise = enterpriseRepo.findByUser(loggedUser);
			model.addAttribute("user", loggedUser); 
			List<Job> jobExpiredByEmployer = jobService.GetAllExpiredJobByEnterprise(enterprise);
			model.addAttribute("jobExpiredByEmployer", jobExpiredByEmployer);
			System.out.println("jobExpiredByEmployer: " + jobExpiredByEmployer.size());
		} else {
			return "redirect:/logon/";
		}
		return "views/employer/job-expired-by-employer";
	}



	@GetMapping("/applied-by-employer/")
	public String viewAppliedCandidateByEmployer(Model model,@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			Enterprise enterprise = enterpriseRepo.findByUser(loggedUser);
			if (enterprise != null){
				Long enterpriseId = enterprise.getId();
				System.out.println("enterpriseId : " + enterpriseId);

				Page<User> users = userService.getApplyCandidateByEnterprise(pageNo, enterpriseId);
				model.addAttribute("totalPage", users.getTotalPages());
				model.addAttribute("currentPage", pageNo);

				model.addAttribute("users", users);
				System.out.println("users size : " + users);
			}else{
				model.addAttribute("message", "null_enterprise");

			}

		} else {
			return "redirect:/logon/";
		}
		return "views/employer/applied-by-employer";
	}




	@GetMapping("/forgotPassword")
	public String forgotPassword(Model model) {
		/*
		 * User user = new User(); user.setEnabled(true); model.addAttribute("user",
		 * user);
		 * 
		 * List<Role> roles = repoRole.findAll(); model.addAttribute("roles", roles);
		 */
		return "views/candidate/fogot-password";
	}
	

	// Check email when register and Send Email to Reset Password
	
	//@ResponseBody  	@PostMapping("/register")

	@PostMapping("/forgotPassword")
	public String SendMailToresetPassword(Model model, 		
			@RequestParam("email") String emailValue,
			HttpServletRequest request) {
		
					// Get baseUrl
			String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
					.replacePath(null)
					.build()
					.toUriString();
		
			System.out.println("baseUrl : " + baseUrl);
			System.out.println("emailValue : " + emailValue);

		
		User user_check = repoUser.findTop1ByEmail(emailValue);
//		if (user_check != null) {
			
			//Send mail to friend
			String from = "tkhn2020@gmail.com";

			String comment = "<p>Hello,</p><p>You requested to Reset the Password?</p>";
			String url_change_pass =  baseUrl + "/candidate/resetpassword/" + emailValue;
			comment = comment +" <a href=" + "'" + url_change_pass +  "'" + ">Click Here to Reset Password</a>";
			comment = comment + " <br> If not you, please ignore this Email!";
		    String subject = "Reset Password";
		    String file = null;
		  
			MailInfo info = new MailInfo(from, emailValue, null,null,subject, comment, file);
			try {
				mailService.send(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
//		} else {
//			return "redirect:/candidate/forgotPassword";
//
//		}
			return "redirect:/candidate/forgotPassword";

	}
	

	// Display form to Reset Password

	@GetMapping("/resetpassword/{email}")
	public String FormResetPass(Model model, @PathVariable String email) {
		System.out.println(email);
		model.addAttribute("email", email);
		return "views/candidate/reset-password";
	}
	
	@PostMapping("/resetUserpassword")
	public String changePassword(Model model,
			@RequestParam("email") String emailValue,
			@RequestParam("passWord") String password
			) {
		System.out.println("emailValue : " + emailValue);
		System.out.println("emailValue : " + emailValue);
	
		User user = repoUser.findTop1ByEmail(emailValue);
		user.setPassWord(bCryptPasswordEncoder.encode(password));
		repoUser.save(user);
		
		return "redirect:/logon";
	}

	
	
	public void getAllList(Model model) {
		List<Enterprise> enterprises = enterpriseRepo.getAllEnterprise();
		model.addAttribute("enterprises", enterprises);
//		List<Category> categories = categoryRepo.findAllByActive();
//		model.addAttribute("categories", categories);

		List<JobCategory> jobcategories = jobCategoryRepo.findAllByActive();
		model.addAttribute("jobcategories", jobcategories);
//		List<Rank> ranks = rankRepo.findAllByActive();
//		model.addAttribute("ranks", ranks);
		List<Location> locations = locationRepo.findAllByActive();
		model.addAttribute("locations", locations);
		List<WorkingModel> workingModels = workingModelRepo.findAllByActive();
		model.addAttribute("workingModels", workingModels);
	}
}
