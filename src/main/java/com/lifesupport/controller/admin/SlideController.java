package com.lifesupport.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.lifesupport.models.Blog;
import com.lifesupport.models.Category;
import com.lifesupport.models.Slide;
import com.lifesupport.repository.SlideRepository;
import com.lifesupport.service.FilesStorageService;
import com.lifesupport.service.SlideService;

@Controller
@RequestMapping("/admin/slide")

public class SlideController {
	@Autowired
	private SlideRepository repo;
	@Autowired
	private SlideService slideService;
	@Autowired
	FilesStorageService storageService;

	// Lấy giá trị config từ file application.properties
   @Value("${upload_slide_path}")
   String upload_path;

	@GetMapping("/add")
	public String showAddPage(Model model) {

//		List<Slide> list = slideService.getAllActiveCate();
//		model.addAttribute("listCate", listCate);

		Slide slide = new Slide();
		model.addAttribute("slide", slide);
		return "/admin/slide/add";
	}

	

	@PostMapping("/add")
	public String addSlide(Model model, @ModelAttribute Slide slide_form, @RequestParam("file") MultipartFile image) {
		Date createAt = new Date();
		String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
//		String upload_path = "public/images/";

		storageService.saveFile(upload_path, image, storageFileName);

		Slide slide = new Slide();
		slide.setTitle(slide_form.getTitle());
		slide.setComment(slide_form.getComment());
		slide.setCreateAt(createAt);
		slide.setFileName(storageFileName);
		slide.setStatus(slide_form.getStatus());
		repo.save(slide);
		return "redirect:list";
//		return "/admin/blog/blogs";
	}

	@GetMapping("/list")
	public String getAllSlideSearchAndPaging(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		Page<Slide> slidelist = slideService.getAll(pageNo);
		if (keyword != null) {
			System.out.println("keyword : " + keyword);

			slidelist = slideService.searchSlide(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", slidelist.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("slidelist", slidelist);
		return "/admin/slide/slides";
	}
	
	@GetMapping("/delete")
	public String deleteSlide( @RequestParam Integer id) {
		try {
			Slide slide = repo.findById(id).get();
			Path imagePath = Paths.get(upload_path + slide.getFileName());
			System.out.println(imagePath);
			try {
				Files.delete(imagePath);
				System.out.println("Deleted : " +imagePath);

			}catch (Exception ex) {
				System.out.print(ex.getMessage());
			}
			//delete 
			repo.delete(slide);
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
		return "redirect:list";
	}


	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam Integer id) {
		
//		List<Category> listCate = categoryService.getAllActiveCate();
//		model.addAttribute("listCate", listCate);
//		Slide slide = repo.findById(id).get();
		Slide slide = repo.findById(id).get();
		model.addAttribute("slide", slide);	
		return "/admin/slide/edit";
	}
	
	
	  
	@PostMapping("/edit")
	public String editSlide(Model model, @RequestParam Integer id, @ModelAttribute Slide slide_form, 
			@RequestParam("file") MultipartFile image) {
		Slide curent_slide = repo.findById(id).get();
		model.addAttribute(curent_slide);
		Date createAt = new Date();

	//	if (!gala.getImageFileName().isBlank()) {
		if (!image.getOriginalFilename().isEmpty()) {
			//Xoa File
//			String upload_path = "public/images/";

			Path imagePath = Paths.get(upload_path + curent_slide.getFileName());
			try {
				System.out.println("Xoa File cũ " + curent_slide.getFileName());

				Files.delete(imagePath);
				System.out.println("Xoa File thanh cong: " );

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
			
			storageService.saveFile(upload_path, image, storageFileName);

			curent_slide.setFileName(storageFileName);

		}else {
			System.out.println("Khong sua File: " );
		}
		
		curent_slide.setTitle(slide_form.getTitle());
		curent_slide.setComment(slide_form.getComment());

		curent_slide.setUpdateAt(createAt);
//		curent_slide.setCategory(blog_form.getCategory());
//		curent_slide.setContent(blog_form.getContent());
		curent_slide.setStatus(slide_form.getStatus());

		repo.save(curent_slide);
		return "redirect:list";
		
		
	  }
}
