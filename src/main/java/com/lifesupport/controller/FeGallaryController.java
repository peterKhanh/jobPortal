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
import com.lifesupport.models.Category;
import com.lifesupport.models.Gallery;
import com.lifesupport.repository.GalleryRepository;
import com.lifesupport.repository.UserRepository;
import com.lifesupport.service.CategoryService;
import com.lifesupport.service.GalleryService;
import com.lifesupport.service.UserService;

@Controller
@RequestMapping("/gallary")
public class FeGallaryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private GalleryRepository galleryRepo;
	@Autowired
	private GalleryService galleryService;
	@Autowired
	private UserRepository repoUser;
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public String getAllGalleryAndPaging(Model model, @Param("keyword") String keyword, @RequestParam(name="pageNo", defaultValue = "1") Integer pageNo, Principal principal) {
		userService.checkLogin(model, principal);

		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);
				
		Page<Gallery> blogs = galleryService.getAll(pageNo);
//		if (keyword != null) {
//			blogs = blogService.searchBlog(keyword, pageNo);
//			model.addAttribute("keyword", keyword);
//		}
		model.addAttribute("totalPage", blogs.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		
		System.out.println("totalPage" + blogs.getTotalPages());
		System.out.println("currentPage" + pageNo);

		model.addAttribute("gallery_list", blogs);
		return "layout/gallary/index";
	}

	

	
	@GetMapping("/{type}")
	public String getAllGalleryByTypeAndPaging(Model model, @PathVariable("type") String type, @RequestParam(name="pageNo", defaultValue = "1") Integer pageNo) {
		
		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);
			
		Page<Gallery> blogs = galleryService.getAll(pageNo);
//		Page<Gallery> blogs = galleryService.getByType(pageNo, type);
		model.addAttribute("totalPage", blogs.getTotalPages());
		model.addAttribute("currentPage", pageNo);		
		System.out.println("totalPage" + blogs.getTotalPages());
		System.out.println("currentPage" + pageNo);
		model.addAttribute("gallery_list", blogs);
		return "layout/gallary/index";
	}

	
	
	@GetMapping("/contact")
	public String Contact(Model model) {
		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);
				

		return "layout/gallary/contact";
	}



	
}
