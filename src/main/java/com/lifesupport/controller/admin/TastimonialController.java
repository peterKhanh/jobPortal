package com.lifesupport.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

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
import com.lifesupport.models.Tastimonial;
import com.lifesupport.repository.TastimonialRepository;
import com.lifesupport.service.FilesStorageService;
import com.lifesupport.service.TastimonialService;

@Controller
@RequestMapping("/admin/tastimonial")

public class TastimonialController {
	@Autowired
	private TastimonialRepository repo;
	@Autowired
	private TastimonialService tastimonialService;
	@Autowired
	FilesStorageService storageService;

	// Lấy giá trị config từ file Const
   String upload_path =ConstantsClass.CONST_TASTIMONIAL_IMAGE_UPLOAD_PATH;

	@GetMapping("/add")
	public String showAddPage(Model model) {
		Tastimonial  slide = new Tastimonial();
		model.addAttribute("slide", slide);
		return "admin/tastimonial/add";
	}
	
	@PostMapping("/add")
	public String addTastimonial(Model model, @ModelAttribute Tastimonial slide_form, @RequestParam("file") MultipartFile image) {
		Date createAt = new Date();
		String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();

		storageService.saveFile(upload_path, image, storageFileName);

		Tastimonial slide = new Tastimonial();
		slide.setTitle(slide_form.getTitle());
		slide.setSubTitle(slide_form.getSubTitle());
		slide.setComment(slide_form.getComment());
		slide.setCreateAt(createAt);
		slide.setFileName(storageFileName);
		slide.setStatus(slide_form.getStatus());
		repo.save(slide);
		return "redirect:list";
	}

	@GetMapping("/list")
	public String getAllTastimonialSearchAndPaging(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		Page<Tastimonial> tastimoniallist = tastimonialService.getAll(pageNo);
		if (keyword != null) {
			System.out.println("keyword : " + keyword);

			tastimoniallist = tastimonialService.searchTastimonial(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", tastimoniallist.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("tastimoniallist", tastimoniallist);
		return "admin/tastimonial/list";
	}
	
	@GetMapping("/delete")
	public String deleteSlide( @RequestParam Integer id) {
		try {
			Tastimonial slide = repo.findById(id).get();
			Path imagePath = Paths.get(upload_path + slide.getFileName());
			try {
				Files.delete(imagePath);
			}catch (Exception ex) {
				System.out.print(ex.getMessage());
			}
			//delete product
			repo.delete(slide);
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
		return "redirect:list";
	}


	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam Integer id) {

		Tastimonial tastimonial = repo.findById(id).get();
		model.addAttribute("tastimonial", tastimonial);	
		return "admin/tastimonial/edit";
	}
	
	
	  
	@PostMapping("/edit")
	public String editTastimonial(Model model, @RequestParam Integer id, @ModelAttribute Tastimonial slide_form, 
			@RequestParam("file") MultipartFile image) {
		Tastimonial curent_slide = repo.findById(id).get();
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
		curent_slide.setSubTitle(slide_form.getSubTitle());
		curent_slide.setComment(slide_form.getComment());

		curent_slide.setUpdateAt(createAt);
//		curent_slide.setCategory(blog_form.getCategory());
//		curent_slide.setContent(blog_form.getContent());
		curent_slide.setStatus(slide_form.getStatus());

		repo.save(curent_slide);
		return "redirect:list";
		
		
	  }
}
