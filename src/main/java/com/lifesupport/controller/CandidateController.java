package com.lifesupport.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.lifesupport.models.JobApply;
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
import com.lifesupport.repository.LocationRepository;
import com.lifesupport.repository.ProfileRepository;
import com.lifesupport.repository.RoleRepository;
import com.lifesupport.repository.UserRepository;
import com.lifesupport.repository.UserRoleRepository;
import com.lifesupport.repository.WorkingModelRepository;
import com.lifesupport.service.FilesStorageService;
import com.lifesupport.service.UserService;
import com.lifesupport.service.MailService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

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
	private RoleRepository repoRole;

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
		return "views/candidate/register";
	}

	@PostMapping("/register")
	public String register(Model model, @ModelAttribute User form_data) {
		String roleName = "CANDIDATE";
//		Role role = repoRole.findByName(roleName);
		// System.out.println("ID: " + id);
		Date createAt = new Date();
//		System.out.println(user.toString());
		System.out.println("Time create: " + createAt);
//		System.out.println("Full Name : " + form_data.getFullName());
//		System.out.println("Email : " + form_data.getEmail());
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
	@GetMapping("/edituser/")
	public String viewEditUser(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("user", loggedUser);

		} else {
			return "redirect:/logon/";
		}
		return "views/candidate/edit-account";
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

		return "redirect:/candidate/account/";
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
		return "views/candidate/change-password";
	}

	@PostMapping("/change-pasword")
	public String changepaswords(@ModelAttribute User user_form, @RequestParam("newPassWord") String newPassWord) {

		Long user_id = user_form.getId();
		System.out.println("User ID : " + user_id);
		User logUser = repoUser.findById(user_id).get();
		String pass_input_encode = bCryptPasswordEncoder.encode(newPassWord);
		logUser.setPassWord(pass_input_encode);
		repoUser.save(logUser);		
		return "redirect:/candidate/";
	}

	@GetMapping("/")
	public String dashboad(Model model, Principal principal) {
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
			return "views/candidate/dashboard";
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
			return "views/candidate/index";
		}else {
			return "redirect:/logon/";
		}
	}


	
	@GetMapping("/profile/")
	public String viewAccountDashboard(Model model, Principal principal) {
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
			return "views/candidate/profiles";
		}else {
			return "redirect:/logon/";
		}
	}

	
	@GetMapping("/add-profile/")
	public String viewAddProfiler(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		getAllList(model);

		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("user", loggedUser);

		} else {
			return "redirect:/logon/";

		}
		return "views/candidate/add-profile";
	}
	

	@PostMapping("/add-profile")
	public String addProfile(Principal principal, @ModelAttribute Profile form) {
		Date createAt = new Date();
		Boolean status = true;
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);

			Profile profile = new Profile();
			profile.setCreateAt(createAt);
			profile.setTitle(form.getTitle());
			profile.setAddress(form.getAddress());
			profile.setBirthdate(form.getBirthdate());
			profile.setPhone(form.getPhone());
			profile.setUrl(form.getUrl());
				
			profile.setUser(loggedUser);
			profile.setStatus(status);
			repoProfile.save(profile);

		} else {
			return "redirect:/logon/";

		}

		return "redirect:/candidate/profile/";
	}

	
	
	
	
	@GetMapping("/view-profile")
	public String viewProfile(Model model, @RequestParam Long id, Principal principal) {
		userService.checkLogin(model, principal);

		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("loggedUser", loggedUser);
			Profile profile = repoProfile.findById(id).get();
			model.addAttribute("profile", profile);
		} else {
			return "redirect:/logon/";
		}
		return "views/candidate/view-profile";
	}

	@GetMapping("/delete")
	public String deleteProfi(@RequestParam Long id) {
		Profile profile = repoProfile.findById(id).get();
	  	try {
			Path cv_1_filepath = Paths.get(upload_path + profile.getCv_1_Filename());		
			Path cv_2_filepath = Paths.get(upload_path + profile.getCv_2_Filename());
			Path cv_3_filepath = Paths.get(upload_path + profile.getCv_3_Filename());

			Path bd_1_filepath = Paths.get(upload_path + profile.getBd_1_Filename());		
			Path bd_2_filepath = Paths.get(upload_path + profile.getBd_2_Filename());
			Path bd_3_filepath = Paths.get(upload_path + profile.getBd_3_Filename());

			try {
				if (cv_1_filepath.isAbsolute())	Files.delete(cv_1_filepath);
				if (cv_2_filepath.isAbsolute())	Files.delete(cv_2_filepath);
				if (cv_3_filepath.isAbsolute())	Files.delete(cv_3_filepath);
				
				if (bd_1_filepath.isAbsolute())	Files.delete(bd_1_filepath);
				if (bd_2_filepath.isAbsolute())	Files.delete(bd_2_filepath);
				if (bd_3_filepath.isAbsolute())	Files.delete(bd_3_filepath);

			} catch (Exception ex) {
				System.out.print(ex.getMessage());
			}
	
		// delete PROFILE
	  		repoProfile.delete(profile);
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
		return "redirect:/candidate/profile/";

	}
	
	@GetMapping("/edit-profile")
	public String editProfile(Model model, @RequestParam Long id, Principal principal) {
		userService.checkLogin(model, principal);
		System.out.println(id.toString());
		getAllList(model);

		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("loggedUser", loggedUser);
			Profile profile = repoProfile.findById(id).get();
			model.addAttribute("profile", profile);

		} else {
			return "redirect:/logon/";

		}

		return "views/candidate/edit-profile";
	}


	@PostMapping("/edit-profile")
	public String editProfile(Model model,Principal principal, @RequestParam Long id, @ModelAttribute Profile form, 
			@RequestParam("cv_1") MultipartFile cv_1,
			@RequestParam("cv_2") MultipartFile cv_2,
			@RequestParam("cv_3") MultipartFile cv_3,
			@RequestParam("bd_1") MultipartFile bd_1,
			@RequestParam("bd_2") MultipartFile bd_2,
			@RequestParam("bd_3") MultipartFile bd_3)
	{
		
		Profile cur_profile = repoProfile.findById(id).get();
		model.addAttribute(cur_profile);
		Date updateAt = new Date();		
		cur_profile.setUpdateAt(updateAt);
		
		cur_profile.setTitle(form.getTitle());
		cur_profile.setBirthdate(form.getBirthdate());
		cur_profile.setPhone(form.getPhone());
		cur_profile.setAddress(form.getAddress());
		cur_profile.setLocation(form.getLocation());
		cur_profile.setWorkingModel(form.getWorkingModel());
		cur_profile.setSkills(form.getSkills());
		cur_profile.setLangugages(form.getLangugages());
		cur_profile.setLicences(form.getLicences());
		cur_profile.setExpericences(form.getExpericences());

		

		// Upload and Edit CV 1
		if (!cv_1.getOriginalFilename().isEmpty()) {
			// Xoa File
			Path filePath = Paths.get(upload_path + cur_profile.getCv_1_Filename());
			try {
				if (cur_profile.getCv_1_Filename() != null) {
					Files.delete(filePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String storageFileName = updateAt.getTime() + "_" + cv_1.getOriginalFilename();
			storageService.saveFile(upload_path, cv_1, storageFileName);
			cur_profile.setCv_1_Filename(storageFileName);

		} else {
			System.out.println("Khong sua File: ");
		}

		

		// Upload and Edit CV 2
		if (!cv_2.getOriginalFilename().isEmpty()) {
			// Xoa File
			Path filePath = Paths.get(upload_path + cur_profile.getCv_2_Filename());
			try {
				if (cur_profile.getCv_2_Filename() != null) {
					Files.delete(filePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String storageFileName = updateAt.getTime() + "_" + cv_2.getOriginalFilename();
			storageService.saveFile(upload_path, cv_2, storageFileName);
			cur_profile.setCv_2_Filename(storageFileName);

		} else {
			System.out.println("Khong sua File: ");
		}


		// Upload and Edit CV 3
		if (!cv_3.getOriginalFilename().isEmpty()) {
			// Xoa File
			Path filePath = Paths.get(upload_path + cur_profile.getCv_3_Filename());
			try {
				if (cur_profile.getCv_3_Filename() != null) {
					Files.delete(filePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String storageFileName = updateAt.getTime() + "_" + cv_3.getOriginalFilename();
			storageService.saveFile(upload_path, cv_3, storageFileName);
			cur_profile.setCv_3_Filename(storageFileName);

		} else {
			System.out.println("Khong sua File: ");
		}

		
		// Upload and Edit Bảng điểm 1
		if (!bd_1.getOriginalFilename().isEmpty()) {
			// Xoa File
			Path filePath = Paths.get(upload_path + cur_profile.getBd_1_Filename());
			try {
				if (cur_profile.getBd_1_Filename() != null) {
					Files.delete(filePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String storageFileName = updateAt.getTime() + "_" + bd_1.getOriginalFilename();
			storageService.saveFile(upload_path, bd_1, storageFileName);
			cur_profile.setBd_1_Filename(storageFileName);

		} else {
			System.out.println("Khong sua File: ");
		}		

		
		
		// Upload and Edit Bảng điểm 2
		if (!bd_2.getOriginalFilename().isEmpty()) {
			// Xoa File
			Path filePath = Paths.get(upload_path + cur_profile.getBd_2_Filename());
			try {
				if (cur_profile.getBd_2_Filename() != null) {
					Files.delete(filePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String storageFileName = updateAt.getTime() + "_" + bd_2.getOriginalFilename();
			storageService.saveFile(upload_path, bd_2, storageFileName);
			cur_profile.setBd_2_Filename(storageFileName);

		} else {
			System.out.println("Khong sua File: ");
		}		
		
		// Upload and Edit Bảng điểm 3
		if (!bd_3.getOriginalFilename().isEmpty()) {
			// Xoa File
			Path filePath = Paths.get(upload_path + cur_profile.getBd_3_Filename());
			try {
				if (cur_profile.getBd_3_Filename() != null) {
					Files.delete(filePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String storageFileName = updateAt.getTime() + "_" + bd_3.getOriginalFilename();
			storageService.saveFile(upload_path, bd_3, storageFileName);
			cur_profile.setBd_3_Filename(storageFileName);

		} else {
			System.out.println("Khong sua File: ");
		}		
		
		
		repoProfile.save(cur_profile);
	

		return "redirect:/candidate/profile/";
	}

		
	
	@GetMapping("/view-apply-job/")
	public String viewApplyJob(Model model, Principal principal) {
		userService.checkLogin(model, principal);
		if (principal != null) {
			String userName = principal.getName();
			User loggedUser = repoUser.findByUserName(userName);
			model.addAttribute("user", loggedUser);
			List<JobApply> jobApplyByUser = jobApplyRepo.findByUser(loggedUser);
			model.addAttribute("jobApplyByUser", jobApplyByUser);

		} else {
			return "redirect:/logon/";
		}
		return "views/candidate/view-apply-job";
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
