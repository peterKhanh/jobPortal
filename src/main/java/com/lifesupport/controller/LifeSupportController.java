package com.lifesupport.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lifesupport.models.Category;
import com.lifesupport.models.Gallery;
import com.lifesupport.models.OurService;
import com.lifesupport.repository.GalleryRepository;
import com.lifesupport.repository.OurServiceRepository;
import com.lifesupport.service.CategoryService;
import com.lifesupport.service.GalleryService;
import com.lifesupport.service.OurServiceService;

@Controller
public class LifeSupportController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private OurServiceRepository galleryRepo;
	@Autowired
	private OurServiceService ourService;

	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("name", "Tôi là Khánh");
		//List<Category> listCate = categoryService.getAllActiveCate();
		//model.addAttribute("list", listCate);

		return "index";
	}
	
//	@RequestMapping("/logon")
//	public String login() {
//		return "/admin/logon";
//	}
//	
//	 @RequestMapping(value= {"/default"}, method = RequestMethod.GET)
//	    public String defaultAfterLogin() {
//	        Collection<? extends GrantedAuthority> authorities;
//	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	        authorities = auth.getAuthorities();
//	        String myRole = authorities.toArray()[0].toString();
//	        String admin = "ADMIN";
//	        if (myRole.equals(admin)) {
//	            return "redirect:/admin";
//	        }
//	        return "redirect:/view";
//	    }
//	 

	@GetMapping("/service")
	public String getAllServiceAndPaging(Model model, @RequestParam(name="pageNo", defaultValue = "1") Integer pageNo) {
		
		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);
//				

		Page<OurService> ourservice = ourService.getAll(pageNo);
//		if (keyword != null) {
//			blogs = blogService.searchBlog(keyword, pageNo);
//			model.addAttribute("keyword", keyword);
//		}
		model.addAttribute("totalPage", ourservice.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		
		System.out.println("totalPage" + ourservice.getTotalPages());
		System.out.println("currentPage" + pageNo);

		model.addAttribute("gallery_list", ourservice);
		return "layout/service/index";
	}
}
