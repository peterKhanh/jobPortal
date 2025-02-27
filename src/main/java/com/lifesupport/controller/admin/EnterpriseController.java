package com.lifesupport.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lifesupport.ConstantsClass;
import com.lifesupport.models.Category;
import com.lifesupport.models.Enterprise;
import com.lifesupport.models.IndustrialType;
import com.lifesupport.models.User;
import com.lifesupport.repository.EnterpriseRepository;
import com.lifesupport.repository.IndustrialTypeRepository;
import com.lifesupport.repository.UserRepository;
import com.lifesupport.service.BlogService;
import com.lifesupport.service.CategoryService;
import com.lifesupport.service.EnterService;
import com.lifesupport.service.FilesStorageService;
import com.lifesupport.service.IndustrialTypeService;
import com.lifesupport.service.UserService;

@Controller
@RequestMapping("/admin/enterprise")
public class EnterpriseController {
	@Autowired
	private EnterpriseRepository repo;
	@Autowired
	private IndustrialTypeRepository industrialTypeRepo;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserRepository repoUser;

	@Autowired
	private IndustrialTypeService industrialTypeService;
	@Autowired
	private EnterService enterService;

	@Autowired
	private UserService userService;
	
	@Autowired
	FilesStorageService storageService;

	// Lấy giá trị config từ file
	String upload_path = ConstantsClass.CONST_ENTERPRISE_UPLOAD_PATH;

	@GetMapping("/list")
	public String getAllEnterpriseSearchAndPaging(Model model, Principal principal, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		
		userService.checkLogin(model, principal);
		
		
		Page<Enterprise> enterprises = enterService.getAll(pageNo);
		if (keyword != null) {
			System.out.println("keyword : " + keyword);

			enterprises = enterService.searchEnterprise(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", enterprises.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		System.out.println("keyword : " + enterprises.getTotalPages());
	
		model.addAttribute("default_logo", true);

		model.addAttribute("enterprises", enterprises);
		return "admin/enterprise/enterprises";
	}

	@GetMapping("/add")
	public String showAddPage(Model model,Principal principal) {
		userService.checkLogin(model, principal);

		List<IndustrialType> industrialType = industrialTypeService.getAllActiveIndustrialType();
		model.addAttribute("industrialType", industrialType);

//		// All BlogTag
////		List<BlogTag> listBlogTag = blogTagRepo.findAll();
////		model.addAttribute("listBlogTag", listBlogTag);
//
		Enterprise enterprise = new Enterprise();
		model.addAttribute("enterprise", enterprise);
		return "admin/enterprise/add";
	}

	@PostMapping("/add")
	public String addEnterprise(Model model, @ModelAttribute Enterprise form, @RequestParam("logo") MultipartFile logo,
			@RequestParam("banner") MultipartFile banner) {
		Date createAt = new Date();

		String logoFileName = createAt.getTime() + "_" + logo.getOriginalFilename();
		String bannerFileName = createAt.getTime() + "_" + banner.getOriginalFilename();

		storageService.saveFile(upload_path, logo, logoFileName);
		storageService.saveFile(upload_path, banner, bannerFileName);

		Enterprise enterprise = new Enterprise();
		enterprise.setName(form.getName());
		enterprise.setCreateAt(createAt);
		enterprise.setIndustrialType(form.getIndustrialType());
		enterprise.setNumberOfEmployee(form.getNumberOfEmployee());
		enterprise.setAddress(form.getAddress());
		enterprise.setWebsite(form.getWebsite());
		enterprise.setDefaultLogo(form.getDefaultLogo());
		enterprise.setIntroduction(form.getIntroduction());
		enterprise.setLogoName(logoFileName);
		enterprise.setBannerName(bannerFileName);
		repo.save(enterprise);

		return "redirect:list";
	}

	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam Long id, Principal principal) {
		userService.checkLogin(model, principal);

		List<IndustrialType> industrialType = industrialTypeService.getAllActiveIndustrialType();
		model.addAttribute("industrialType", industrialType);
		Enterprise enterprise = repo.findById(id).get();
		model.addAttribute("enterprise", enterprise);
		return "admin/enterprise/edit";
	}

	@PostMapping("/edit")
	public String editEnterprise(Model model, @RequestParam Long id, @ModelAttribute Enterprise form,
			@RequestParam("logo") MultipartFile logo, @RequestParam("banner") MultipartFile banner) {
		Enterprise curent_enterprise = repo.findById(id).get();
		model.addAttribute(curent_enterprise);
		Date updateAt = new Date();

		// Xoa Logo cu neu cos thay doi
		if (!logo.getOriginalFilename().isEmpty()) {
			// Xoa File
			Path imageLogoPath = Paths.get(upload_path + curent_enterprise.getLogoName());
			if (imageLogoPath.isAbsolute()) {
				try {
					System.out.println("Xoa File cũ " + curent_enterprise.getLogoName());
					Files.delete(imageLogoPath);
					System.out.println("Xoa File thanh cong: ");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String logoFileName = updateAt.getTime() + "_" + logo.getOriginalFilename();

			storageService.saveFile(upload_path, logo, logoFileName);

			curent_enterprise.setLogoName(logoFileName);

		} else {
			System.out.println("Khong sua Logo: ");
		}

		// Xoa Banner cu neu cos thay doi
		if (!banner.getOriginalFilename().isEmpty()) {
			// Xoa File
			Path imageBannerPath = Paths.get(upload_path + curent_enterprise.getBannerName());
			if (imageBannerPath.isAbsolute()) {
				try {
					System.out.println("Xoa File cũ " + curent_enterprise.getBannerName());
					Files.delete(imageBannerPath);
					System.out.println("Xoa File thanh cong: ");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String bannerFileName = updateAt.getTime() + "_" + banner.getOriginalFilename();

			storageService.saveFile(upload_path, banner, bannerFileName);

			curent_enterprise.setBannerName(bannerFileName);

		} else {
			System.out.println("Khong sua Logo: ");
		}

		curent_enterprise.setName(form.getName());
		curent_enterprise.setUpdateAt(updateAt);
		curent_enterprise.setIndustrialType(form.getIndustrialType());
		curent_enterprise.setNumberOfEmployee(form.getNumberOfEmployee());
		curent_enterprise.setAddress(form.getAddress());
		curent_enterprise.setWebsite(form.getWebsite());
		curent_enterprise.setDefaultLogo(form.getDefaultLogo());

		curent_enterprise.setIntroduction(form.getIntroduction());
		repo.save(curent_enterprise);

		return "redirect:list";

	}

	@GetMapping("/delete")
	public String deleteBlog(@RequestParam Long id) {
		try {
			Enterprise enterprise = repo.findById(id).get();
			Path logoFilePath = Paths.get(upload_path + enterprise.getLogoName());
			Path bannerFilePath = Paths.get(upload_path + enterprise.getBannerName());

			try {
				Files.delete(logoFilePath);
				Files.delete(bannerFilePath);
			} catch (Exception ex) {
				System.out.print(ex.getMessage());
			}
			// delete product
			repo.delete(enterprise);
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
		return "redirect:list";

	}

}
