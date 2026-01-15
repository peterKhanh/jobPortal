package com.lifesupport.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.lifesupport.models.Blog;
import com.lifesupport.models.BlogTag;
import com.lifesupport.models.Category;
import com.lifesupport.models.BlogCate;
import com.lifesupport.repository.BlogRepository;
import com.lifesupport.repository.BlogTagRepository;
import com.lifesupport.service.BlogCateService;
import com.lifesupport.service.BlogService;
import com.lifesupport.service.CategoryService;
import com.lifesupport.service.FilesStorageService;

import jakarta.persistence.Entity;

@Controller
@RequestMapping("/admin/blog")
public class BlogController {
	@Autowired
	private BlogRepository repo;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BlogCateService blogcateService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private BlogTagRepository blogTagRepo;
	@Autowired
	FilesStorageService storageService;

	// Lấy giá trị config từ file
	String upload_path = ConstantsClass.CONST_BLOG_UPLOAD_PATH;

	@GetMapping("/add")
	public String showAddPage(Model model) {

		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);
		List<BlogCate> listBlogcate = blogcateService.getAllActiveBlogCate();
		model.addAttribute("listBlogcate", listBlogcate);

		// All BlogTag
		List<BlogTag> listBlogTag = blogTagRepo.findAll();
		model.addAttribute("listBlogTag", listBlogTag);

		Blog blog = new Blog();
		model.addAttribute("blog", blog);
		return "/admin/blog/add";
	}

	@PostMapping("/add")
	public String addBlog(Model model, @ModelAttribute Blog blog_form,
			@ModelAttribute("hd_list_tag") ArrayList<Integer> tag, @RequestParam("file") MultipartFile image) {
		Date createAt = new Date();
		String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();

		storageService.saveFile(upload_path, image, storageFileName);
		System.out.println();
		System.out.println("Tag Size:    " + tag.size());
		System.out.println(" Tag value   : " + tag);


		BlogTag blogTag;
		List<BlogTag> listBlogTag = new ArrayList<>();

		System.out.println();
		if (tag.size() > 0) {
			for (Integer tagId : tag) {
				blogTag = blogTagRepo.findById(tagId).get();
				listBlogTag.add(blogTag);
				/*
				 * System.out.println("Tag Size:    " + tag.size());
				 	System.out.println(" Tag value   : " + tag);
				 */
			}
		} else {

			System.out.println("Tag is Null    ");

		}

		
		Blog blog = new Blog();
		blog.setTitle(blog_form.getTitle());
		blog.setCreateAt(createAt);
		blog.setShortcontent(blog_form.getShortcontent());
		blog.setCategory(blog_form.getCategory());
		blog.setBlogcate(blog_form.getBlogcate());
			
		blog.setContent(blog_form.getContent());
		blog.setCategory(blog_form.getCategory());
		blog.setImageFileName(storageFileName);
		blog.setTags(listBlogTag);
		repo.save(blog);
		return "redirect:list";
	}

	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam Integer id) {

		List<Category> listCate = categoryService.getAllActiveCate();
		model.addAttribute("listCate", listCate);
		List<BlogCate> listBlogcate = blogcateService.getAllActiveBlogCate();
		model.addAttribute("listBlogcate", listBlogcate);
		
		Blog blog = repo.findById(id).get();
		model.addAttribute("blog", blog);

		// All BlogTag
		List<BlogTag> listBlogTag = blogTagRepo.findAll();
		model.addAttribute("listBlogTag", listBlogTag);

		//Get Blog Tag by Blog ID
		List<BlogTag> listBlogTagByBlogId = blogTagRepo.findByBlogId(id);
		System.out.println("listBlogTagByBlogId: " + listBlogTagByBlogId.size());

//		model.addAttribute("listBlogTag", listBlogTagByBlogId);
		BlogTag entities;
		List<Integer> ids = listBlogTagByBlogId.stream().map(BlogTag::getId).collect(Collectors.toList());
		model.addAttribute("id_list", ids);

		return "/admin/blog/edit2";
	}

	@PostMapping("/edit")
	public String editBlog(Model model, @RequestParam Integer id, @ModelAttribute Blog blog_form,
			@ModelAttribute("hd_list_tag") ArrayList<Integer> tag, @RequestParam("file") MultipartFile image) {
		Blog curent_blog = repo.findById(id).get();
		model.addAttribute(curent_blog);
		Date createAt = new Date();

		if (!image.getOriginalFilename().isEmpty()) {
			// Xoa File

			Path imagePath = Paths.get(upload_path + curent_blog.getImageFileName());
			try {
				System.out.println("Xoa File cũ " + curent_blog.getImageFileName());
				Files.delete(imagePath);
				System.out.println("Xoa File thanh cong: ");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();

			storageService.saveFile(upload_path, image, storageFileName);

			curent_blog.setImageFileName(storageFileName);

		} else {
			System.out.println("Khong sua File: ");
		}

		BlogTag blogTag;
		List<BlogTag> listBlogTag = new ArrayList<>();

		System.out.println();
		if (tag.size() > 0) {
			for (Integer tagId : tag) {
				blogTag = blogTagRepo.findById(tagId).get();
				listBlogTag.add(blogTag);
				/*
				 * System.out.println("Tag Size:    " + tag.size());
				 	System.out.println(" Tag value   : " + tag);
				 */
			}
		} else {

			System.out.println("Tag is Null    ");

		}

		curent_blog.setTitle(blog_form.getTitle());
		curent_blog.setShortcontent(blog_form.getShortcontent());

		curent_blog.setUpdateAt(createAt);
		curent_blog.setCategory(blog_form.getCategory());
		curent_blog.setBlogcate(blog_form.getBlogcate());

		curent_blog.setContent(blog_form.getContent());
		curent_blog.setTags(listBlogTag);
		repo.save(curent_blog);
		return "redirect:list";

	}

	@GetMapping("/delete")
	public String deleteBlog(@RequestParam Integer id) {
		try {
			Blog blog = repo.findById(id).get();
			Path imagePath = Paths.get(upload_path + blog.getImageFileName());
			try {
				Files.delete(imagePath);
			} catch (Exception ex) {
				System.out.print(ex.getMessage());
			}
			// delete product
			repo.delete(blog);
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
		return "redirect:list";

	}

	@GetMapping("/list")
	public String getAllBlogSearchAndPaging(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		Page<Blog> blogs = blogService.getAll(pageNo);
		if (keyword != null) {
			System.out.println("keyword : " + keyword);

			blogs = blogService.searchBlog(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", blogs.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("blogs", blogs);
		return "/admin/blog/blogs";
	}

}
