package com.lifesupport.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lifesupport.ConstantsClass;
import com.lifesupport.models.Blog;
import com.lifesupport.models.Category;
import com.lifesupport.models.Comment;
import com.lifesupport.models.Job;
import com.lifesupport.models.JobApply;
import com.lifesupport.models.MailInfo;
import com.lifesupport.models.Profile;
import com.lifesupport.models.Role;
import com.lifesupport.models.User;
import com.lifesupport.models.UserRole;
import com.lifesupport.repository.JobApplyRepository;
import com.lifesupport.repository.JobRepository;
import com.lifesupport.repository.ProfileRepository;
import com.lifesupport.repository.RoleRepository;
import com.lifesupport.repository.UserRepository;
import com.lifesupport.repository.UserRoleRepository;
import com.lifesupport.service.CategoryService;
import com.lifesupport.service.FilesStorageService;
import com.lifesupport.service.MailService;
import com.lifesupport.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

//import com.eshop.models.Category;
//import com.eshop.models.Role;
//import com.eshop.models.User;
//import com.eshop.models.UserRole;
//import com.eshop.repository.RoleRepository;
//import com.eshop.repository.UserRepository;
//import com.eshop.repository.UserRoleRepository;
//import com.eshop.services.CategoryService;
//import com.eshop.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserRepository repoUser;
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private RoleRepository repoRole;
	@Autowired
	private UserRoleRepository repoUserRole;
	@Autowired
	private JobRepository repoJob;

	@Autowired
	private ProfileRepository repoProfile;

	@Autowired
	private JobApplyRepository repoJobApply;

	@Autowired
	private UserService userService;

	@Autowired
	FilesStorageService storageService;

	@Autowired
	MailService mailService;

	String upload_path = ConstantsClass.CONST_USER_IMAGE_UPLOAD_PATH;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/admin/users")
	public String getAllUser(Model model, Principal principal, @Param("keyword") String keyword,
			@RequestParam(defaultValue = "1") Integer pageNo) {
//		List<User> listUser = repoUser.findAll();
		userService.checkLogin(model, principal);

		Page<User> listUser = userService.getAllSystemAdminAndUserAdmin(pageNo);
		if (keyword != null) {

			listUser = userService.SearchSystemUserAndUserAdmin(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", listUser.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("listUser", listUser);

		System.out.println(listUser.toString());

		return "admin/user/index";
	}

	@GetMapping("/admin/candidates")
	public String getAllCandidate(Model model, Principal principal, @Param("keyword") String keyword,
			@RequestParam(defaultValue = "1") Integer pageNo) {
		userService.checkLogin(model, principal);
		Page<User> listUser = userService.getAllCandidate(pageNo);
		System.out.println(listUser.toString());
		
		if (keyword != null) { 
			listUser = userService.searchUser(keyword, pageNo);
			model.addAttribute("keyword", keyword); 
		}
		
		model.addAttribute("totalPage", listUser.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("listUser", listUser);
		return "admin/user/candidates";
	}
	
	@GetMapping("/admin/candidates-list")
	public String getAllCandidate_(Model model, Principal principal, @Param("keyword") String keyword,
			@RequestParam(defaultValue = "1") Integer pageNo) {
		userService.checkLogin(model, principal);
		Page<User> listUser = userService.getAllCandidate(pageNo);
		System.out.println(listUser.toString());
		
		if (keyword != null) { 
			listUser = userService.searchUser(keyword, pageNo);
			model.addAttribute("keyword", keyword); 
		}
		
		model.addAttribute("totalPage", listUser.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("listUser", listUser);
		return "admin/user/candidates-list";
	}
	
	@GetMapping("/admin/user-recent-apply")
	public String getAllCandidate(Model model, @RequestParam(defaultValue = "1") Integer pageNo) {
//		List<User> listUser = repoUser.findAll();
		Pageable pageable = PageRequest.of(pageNo - 1, 10);
		Page<JobApply> recentapplyusers = repoJobApply.findRecentApplyCandidate(pageable);
		System.out.println(recentapplyusers.toString());
		model.addAttribute("totalPage", recentapplyusers.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("recentapplyusers", recentapplyusers);
		return "admin/user/recentapplyusers";
	}
	/*
	 * Lấy thông tin hồ sơ của ứng viên
	 * lấy danh sách các Job đã ứng tuyển
	 * lấy các JOb đã lưu của ứng viên
	 * */
	@GetMapping("/admin/user-view-profile/{id}")
	public String ViewProfile(Model model, @PathVariable Long id,
			@RequestParam(defaultValue = "1") Integer pageNo) {
		User user = repoUser.findById(id).get();
		model.addAttribute("user", user);

		System.out.println(user.getFullName());
		List<Profile> profiles = repoProfile.findAllByUser(user);
		// Thong tin ho sơ ứng viên đã tạo
		if (!profiles.isEmpty()) {
			Profile profile  = profiles.get(0);
			model.addAttribute("profile", profile);
			System.out.println("Da co ho so");
		}else {
			System.out.println("Ong nay chua có hồ sơ");
		}
		// Lay danh sách cac Job đã ứng tuyển

		/*
		 * Pageable pageable = PageRequest.of(pageNo - 1, 10); 
		 * Page<JobApply> jobApplyByUser = repoJobApply.findByUser(user, pageable);
		 * 
		 * model.addAttribute("totalPage", jobApplyByUser.getTotalPages());
		 * model.addAttribute("currentPage", pageNo);
		 */
		
		List<JobApply> jobApplyByUser = repoJobApply.findByUser(user);	
//		Pageable pageable = PageRequest.of(pageNo - 1, 10);
//		Page<JobApply> jobApplyByUser = repoJobApply.findByUser(user, pageable);
		
		System.out.println("Da ưng tuyen: " + user.getFullName() + " -  " + jobApplyByUser.size());
		model.addAttribute("jobApplyByUser", jobApplyByUser);

		
		

		List<Job> recentjobs = repoJob.findTop10ByOrderByCreateAtDesc();
		model.addAttribute("recentjobs", recentjobs);
		
		
		return "admin/user/view-profile";

	}

	@GetMapping("/admin/user-recent-apply-by-job")
	public String getAllCandidate(Model model, @RequestParam Long id,
			@RequestParam(defaultValue = "1") Integer pageNo) {
		System.out.println(id.toString());
		Job currentJob = repoJob.findById(id).get();

		Pageable pageable = PageRequest.of(pageNo - 1, 10);
		Page<JobApply> userbyjob = repoJobApply.findByJob(currentJob, pageable);

		model.addAttribute("totalPage", userbyjob.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("userbyjob", userbyjob);
		return "admin/user/candidate-by-job";
	}

	@GetMapping("/admin/add-user")
	public String addNewUser(Model model) {
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);

		List<Role> roles = repoRole.findAll();
		model.addAttribute("roles", roles);

		return "admin/user/add-user";
	}

	/*
	 * @GetMapping("/admin/add-user") public String addUser(Model model) { User user
	 * = new User(); user.setEnabled(true); model.addAttribute("user", user);
	 * 
	 * List<Role> roles = repoRole.findAll(); model.addAttribute("roles", roles);
	 * 
	 * return "admin/user/add"; }
	 */

	@PostMapping("/admin/add-user")
	public String save(@ModelAttribute User user) {
		

		user.setPassWord(bCryptPasswordEncoder.encode(user.getPassWord()));
		if (this.userService.create(user)) {
			UserRole userRole = new UserRole();
			Role role = repoRole.findByName("ADMIN");
			userRole.setUser(user);
			userRole.setRole(role);

			repoUserRole.save(userRole);

			return "redirect:/admin/users";
		} else {
			return "user/add";
		}
	}

	@GetMapping("/admin/edit-user/{id}")
	public String edit(Model model, @PathVariable Long id) {
		User user = repoUser.findById(id).get();
		model.addAttribute("user", user);

		List<Role> roles = repoRole.findAll();
		model.addAttribute("roles", roles);

		UserRole userRole;
		userRole = repoUserRole.findByUser(user);
		Long userRoleSelected = null;
		if (userRole != null) {
			userRoleSelected = userRole.getRole().getId();
			model.addAttribute("userRoleSelected", userRoleSelected);
		}
		System.out.println(userRoleSelected);
		return "admin/user/edit";
	}

	@PostMapping("/admin/edit-user")
	public String update(@ModelAttribute User user, @RequestParam("role") String roleId) {
		/*
		 * System.out.println(roleId); Long id = Long.parseLong(roleId); UserRole
		 * userRole; userRole = repoUserRole.findByUser(user);
		 * 
		 * Role role = repoRole.findById(id).get(); if (userRole != null) {
		 * System.out.println("User have Roll"); userRole.setRole(role);
		 * repoUserRole.save(userRole); } else { System.out.println("Do not have Roll");
		 * userRole = new UserRole(); userRole.setUser(user); userRole.setRole(role);
		 * repoUserRole.save(userRole); }
		 */
		user.setPassWord(bCryptPasswordEncoder.encode(user.getPassWord()));

		if (this.userService.create(user)) {
			return "redirect:/admin/users";
		} else {
			return "admin/user/add";
		}
	}

	@GetMapping("/admin/delete-user/{id}")
	public String delete(@PathVariable Long id) {
		if (this.userService.delete(id)) {
			return "redirect:/admin/users";
		} else {
			return "redirect:/admin/users";
		}
	}

// Register new User	
	@ResponseBody
	@GetMapping("/register/newuser")
	public Object[] registerNewUser(Model model, User form_data) {
		// System.out.println("ID: " + id);
		Date createAt = new Date();
		System.out.println(form_data.toString());
		System.out.println("Time comment: " + createAt);
		System.out.println("Full Name : " + form_data.getFullName());
		System.out.println("Email : " + form_data.getEmail());
		System.out.println("User name: " + form_data.getUserName());
		System.out.println("Pasword: " + form_data.getPassWord());

		User user = new User();
		user.setFullName(form_data.getFullName());
		user.setUserName(form_data.getUserName());
		user.setEmail(form_data.getEmail());
		// user.setPassWord(form_data.getPassWord());
		user.setPassWord(bCryptPasswordEncoder.encode(form_data.getPassWord()));

		user.setEnabled(false);
		if (this.userService.create(user)) {
			UserRole userRole = new UserRole();
			Role role = repoRole.findByName("USER");
			userRole.setUser(user);
			userRole.setRole(role);
			repoUserRole.save(userRole);
			Object[] info = { createAt, "Xin chào " + form_data.getFullName() + " ban đa dang ky thanh cong" };
			return info;
		} else {
			return null;
		}

	}

	// Check username when register

	@ResponseBody
	@GetMapping("/register/checkusername/{usernameValue}")
	public String CheckUsername(Model model, @PathVariable("usernameValue") String usernameValue) {
		System.out.println("usernameValue : " + usernameValue);
		User user_check = repoUser.findByUserName(usernameValue);

		if (user_check != null) {
			return "EXIST";

		} else {
			return "NOT";
		}
	}

	// Check email when register
	@ResponseBody
	@GetMapping("/register/checkemail/{emailValue}")
	public String CheckUserEmail(Model model, @PathVariable("emailValue") String emailValue) {
		System.out.println("usernameValue : " + emailValue);
		User user_check = repoUser.findTop1ByEmail(emailValue);
		if (user_check != null) {
			return "EXIST";

		} else {
			return "NOT";
		}
	}

	// Check email when register and Send Email to Reset Password
	@ResponseBody
	@GetMapping("/register/resetPassword/{emailValue}")
	public String SendMailToresetPassword(Model model, @PathVariable("emailValue") String emailValue,
			HttpServletRequest request) {

		// Get baseUrl
		String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();

		System.out.println("usernameValue : " + emailValue);

		User user_check = repoUser.findTop1ByEmail(emailValue);
		if (user_check != null) {

			// Send mail to friend
			String from = "tkhn2020@gmail.com";

			String comment = "<p>Hello,</p><p>You requested to Reset the Password?</p>";
			String url_change_pass = baseUrl + "/register/viewresetpass/" + emailValue;
			comment = comment + " <a href=" + "'" + url_change_pass + "'" + ">Click Here to Reset Password</a>";
			comment = comment + " <br> If not you, please ignore this Email!";
			String subject = "Reset Password";
			String file = null;

			MailInfo info = new MailInfo(from, emailValue, null, null, subject, comment, file);
			try {
				mailService.send(info);
				return "SEND_MAIL_SUCCESS";

			} catch (Exception e) {
				e.printStackTrace();
				return "SEND_MAIL_FALSE";
			}
		} else {
			return "NO_EXIST";
		}
	}

	// Display form to Reset Password

	@GetMapping("/register/viewresetpass/{email}")
	public String FormResetPass(Model model, @PathVariable String email) {
		System.out.println(email);
		model.addAttribute("email", email);
		return "layout/account/reset_password";
	}

	// Check email when register and Send Email to Reset Password
	@ResponseBody
	@GetMapping("/register/changePassword/{emailValue}")
	public String changePassword(Model model, @PathVariable("emailValue") String emailValue, User form_data) {
		System.out.println("emailValue : " + emailValue);
		String password = form_data.getPassWord();
		User user = repoUser.findTop1ByEmail(emailValue);
		user.setPassWord(bCryptPasswordEncoder.encode(password));
		repoUser.save(user);
		System.out.println("fullname: " + user.getFullName());
		System.out.println("Pasword: " + form_data.getPassWord());

		return "OK";
	}

	// Get User Info to Edit Account
	@GetMapping("/user/profile/{id}")
	public String ViewUser(Model model, @PathVariable Long id, Principal principal) {
		userService.checkLogin(model, principal);
		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);
		User user = repoUser.findById(id).get();
		model.addAttribute("user", user);
		return "layout/account/view-profile";
	}

	@PostMapping("/user/edit-userprofile")
	public String updateUserProfile(@ModelAttribute User user_form, @RequestParam("file_image") MultipartFile image) {

		System.out.println("User n: " + user_form.getFullName());

		Long user_id = user_form.getId();
		System.out.println("User ID : " + user_id);

		User updateUser = repoUser.findById(user_id).get();

		updateUser.setFullName(user_form.getFullName());
		updateUser.setAddress(user_form.getAddress());
		updateUser.setEmail(user_form.getEmail());
		updateUser.setTelephone(user_form.getTelephone());

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
		// this.userService.create(updateUser) ;

		/*
		 * UserRole userRole; userRole = repoUserRole.findByUser(user);
		 * 
		 * Role role = repoRole.findById(id).get(); if (userRole != null) {
		 * System.out.println("User have Roll");
		 * 
		 * userRole.setRole(role); repoUserRole.save(userRole); } else {
		 * System.out.println("Do not have Roll"); userRole = new UserRole();
		 * userRole.setUser(user); userRole.setRole(role); repoUserRole.save(userRole);
		 * }
		 */
//			userRole.setRole(role);

//		System.out.println("User Pass: " + user.getPassWord());
//		System.out.println("User Pass Encode: " + bCryptPasswordEncoder.encode(user.getPassWord()));
//		System.out.println("User Pass Encode 123456: " + bCryptPasswordEncoder.encode("123456"));
//		
		/*
		 * user.setPassWord(bCryptPasswordEncoder.encode(user.getPassWord())); if
		 * (this.userService.create(user)) { return "redirect:/admin/users"; } else {
		 * return "admin/user/add"; }
		 */

		return "redirect:/user/profile/" + user_id;
	}

	@GetMapping("/register/forgotPass")
	public String forgotPassword(Model model) {
		/*
		 * User user = new User(); user.setEnabled(true); model.addAttribute("user",
		 * user);
		 * 
		 * List<Role> roles = repoRole.findAll(); model.addAttribute("roles", roles);
		 */
		return "layout/account/forgotPassword";

	}

	@ResponseBody
	@GetMapping("/admin/sendJob/{jobId}/{userEmail}")
	public boolean sendJob(Model model, 
			@PathVariable("jobId") String jobId,
			@PathVariable("userEmail") String userEmail,
			HttpServletRequest request) {
		
		System.out.println("jobId : " + jobId);
		System.out.println("userEmail : " + userEmail);

		Job job = repoJob.findById(Long.parseLong(jobId)).get();
		
		// Get baseUrl
		String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
				.replacePath(null)
				.build()
				.toUriString();
		
	
		// http://localhost/job/viewjob/41
		
		String from = "tkhn2020@gmail.com";

		String body_email = "<p>Xin chào bạn!</p>";
		body_email = body_email + "<p>Chúng tôi thấy có công việc đang tuyển dụng phù hợp với bạn</p>";
		body_email = body_email + "<p>Hãy xem chi tiết mô tả công việc và ứng tuyển </p>";
	
		String url_job_recommend =  baseUrl + "/job/viewjob/" + jobId;
		
		body_email = body_email +" <a href=" + "'" + url_job_recommend +  "'" + "><span>" + job.getTitle() + "</span> </a>";
	    String subject = "Job Portal - Giới thiệu công việc đang tuyển !";
	    String file = null;
	  
		MailInfo info = new MailInfo(from, from, null,null,subject, body_email, file);
//		MailInfo info = new MailInfo(from, userEmail, null,null,subject, body_email, file);
		
		//Send mail to Candidate
//		MailInfo info = new MailInfo(from, "tkhn2020@gmail.com","Thong tin hang hoa", "Nio dung Email");
		//info.setSubject("Thong tin hang hoa");
		try {
			mailService.send(info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
