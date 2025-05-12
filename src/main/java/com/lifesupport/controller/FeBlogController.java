package com.lifesupport.controller;

import java.security.Principal;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifesupport.models.Blog;
import com.lifesupport.models.BlogTag;
import com.lifesupport.models.Category;
import com.lifesupport.models.Comment;
import com.lifesupport.models.User;
import com.lifesupport.repository.BlogRepository;
import com.lifesupport.repository.BlogTagRepository;
import com.lifesupport.repository.CommentRepository;
import com.lifesupport.repository.UserRepository;
import com.lifesupport.service.BlogService;
import com.lifesupport.service.CategoryService;
import com.lifesupport.service.CommentService;
import com.lifesupport.service.UserService;

import jakarta.servlet.http.Cookie;



@Controller
@RequestMapping("/blog")
public class FeBlogController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BlogRepository blogRepo;
	@Autowired
	private BlogService blogService;
	@Autowired
	private CommentRepository commentRepo;
	@Autowired
	private BlogTagRepository blogTagRepo;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public String getAllBlogSearchAndPaging(Model model, @Param("keyword") String keyword, @RequestParam(name="pageNo", defaultValue = "1") Integer pageNo, Principal principal) {
		userService.checkLogin(model, principal);

		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);
				
		Page<Blog> blogs = blogService.getAll(pageNo);
		if (keyword != null) {
			blogs = blogService.searchBlog(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		System.out.println(blogs.getTotalPages());
		model.addAttribute("totalPage", blogs.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		
		model.addAttribute("blogs", blogs);
		return "views/blog/list";
		
	}

	
	/*
	 * return List product by Category
	 * 
	 */
	@GetMapping("/blogbycate/{id}")
	public String shopGetBlogByCate(Model model, @PathVariable("id") Integer id,  @RequestParam(name="pageNo", defaultValue = "1") Integer pageNo, Principal principal) {
		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);
		
		userService.checkLogin(model, principal);
		
		
//		Get Curent Cate
		Category current_cate = categoryService.find(id);
		model.addAttribute("current_cate", current_cate);

		Page<Blog> blogs = blogService.getAllByCate(pageNo, id);
//		if (id != null) {
//			listproduct = productService.searchProduct(keyword, pageNo);
//			model.addAttribute("keyword", keyword);
//		}
		System.out.println("Total page: :" + blogs.getTotalPages());
		System.out.println("Page No: :" + pageNo);

		model.addAttribute("totalPage", blogs.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		
		model.addAttribute("blogs", blogs);
		return "views/blog/list";
	}

	@GetMapping("/view/{id}")
	public String GetBlogDetail(Model model, @PathVariable("id") Integer id, Principal principal) {

		userService.checkLogin(model, principal);
		
		//Get All Active Category for side bar
		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);

		//Get Blog detail
		Blog blog = blogRepo.findById(id).get();
		model.addAttribute("blog", blog);
		
		
//		Get Curent Cate
		Category current_cate = blog.getCategory();
		model.addAttribute("current_cate", current_cate);

// 		Get All BlogTag for Blog
		
		List<BlogTag> listBlogTag = blogTagRepo.findByBlogId(id);
//		System.out.println("listBlogTag.size: " + listBlogTag.size());
		model.addAttribute("listBlogTag", listBlogTag);

		
		//Update viewcount 
		int v_viewcount=0;
		if (blog.getCount() != null) {
			v_viewcount = blog.getCount() + 1;
		}else {
			v_viewcount = 1;
			System.out.println(" Null :" + v_viewcount);
		}
		

		blog.setCount(v_viewcount);
		blogRepo.save(blog);
		
		List<Comment> list_comment =	commentRepo.findByBlogOrderByCreateAtDesc(blog);
		
		model.addAttribute("total_comment", list_comment.size());
		model.addAttribute("list_comment", list_comment);


		return "views/blog/view_blog";
	}

	@ResponseBody
	@GetMapping("/add-comment/{id}")
	public Object[] addComment(Model model, @PathVariable("id") Integer id, Comment form_data) {
		System.out.println("ID: " + id);
		Date createAt = new Date();
		System.out.println("Time comment: " + createAt);
		System.out.println("Name comment: " + form_data.getName());
		System.out.println("Email comment: " + form_data.getEmail());
		System.out.println("Comment comment: " + form_data.getContent());
		
		Blog blog = blogRepo.findById(id).get();
		
		Comment comment = new Comment();
		comment.setName(form_data.getName());
		comment.setEmail(form_data.getEmail());
		comment.setContent(form_data.getContent());
		comment.setCreateAt(createAt);
		comment.setBlog(blog);
		comment.setStatus(false);
		commentService.create(comment);
		
		Object[] info = { createAt, form_data.getName()};
		return info;
	}
	


	@ResponseBody
	@GetMapping("/send-contact")
	public Object[] sendContact() {
		System.out.println("ID: Sending Contact form ");
/*
		Date createAt = new Date();
		System.out.println("Time comment: " + createAt);
		System.out.println("Name comment: " + form_data.getName());
		System.out.println("Email comment: " + form_data.getEmail());
		System.out.println("Comment comment: " + form_data.getContent());
		
		Blog blog = blogRepo.findById(id).get();
		
		Comment comment = new Comment();
		comment.setName(form_data.getName());
		comment.setEmail(form_data.getEmail());
		comment.setContent(form_data.getContent());
		comment.setCreateAt(createAt);
		comment.setBlog(blog);
		comment.setStatus(false);
		commentService.create(comment);
		*/
		Object[] info = {"adb" ,"kh h"};
		return info;
	}


}
