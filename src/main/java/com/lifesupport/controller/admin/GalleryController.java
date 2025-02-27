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
import com.lifesupport.models.Gallery;
import com.lifesupport.repository.GalleryRepository;
import com.lifesupport.repository.SlideRepository;
import com.lifesupport.service.FilesStorageService;
import com.lifesupport.service.GalleryService;
import com.lifesupport.service.SlideService;

@Controller
@RequestMapping("/admin/gallery")

public class GalleryController {
	@Autowired
	private GalleryRepository repo;
	@Autowired
	private GalleryService galleryService ;
	@Autowired
	FilesStorageService storageService;

	// Lấy giá trị config từ file application.properties
   @Value("${upload_gallery_path}")
   String upload_path;

	@GetMapping("/editor")
	public String showEditor(Model model) {

//		List<Slide> list = slideService.getAllActiveCate();
//		model.addAttribute("listCate", listCate);

		Gallery gallery  = new Gallery();
		model.addAttribute("gallery", gallery);
		return "/admin/gallery/editor";
	}

   
	@GetMapping("/add")
	public String showAddPage(Model model) {

//		List<Slide> list = slideService.getAllActiveCate();
//		model.addAttribute("listCate", listCate);

		Gallery gallery  = new Gallery();
		model.addAttribute("gallery", gallery);
		return "/admin/gallery/add";
	}

	

	@PostMapping("/add")
	public String addGallery(Model model, @ModelAttribute Gallery gallery_form, @RequestParam("file") MultipartFile image) {
		Date createAt = new Date();
		String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
//		String upload_path = "public/images/";

		storageService.saveFile(upload_path, image, storageFileName);

		Gallery gallery = new Gallery();
		gallery.setTitle(gallery_form.getTitle());
		gallery.setType(gallery_form.getType());
		gallery.setCreateAt(createAt);
		gallery.setFileName(storageFileName);
		gallery.setStatus(gallery_form.getStatus());
		repo.save(gallery);
		return "redirect:list";
//		return "/admin/blog/blogs";
	}

	@GetMapping("/list")
	public String getAllGallerySearchAndPaging(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		Page<Gallery> gallerylist = galleryService.getAll(pageNo);
		if (keyword != null) {
			System.out.println("keyword : " + keyword);

			gallerylist = galleryService.searchGallery(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", gallerylist.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("slidelist", gallerylist);
		return "/admin/gallery/list";
	}
	
	@GetMapping("/delete")
	public String deleteSlide( @RequestParam Integer id) {
		try {
			Gallery gallery  = repo.findById(id).get();
			Path imagePath = Paths.get(upload_path + gallery.getFileName());
			try {
				Files.delete(imagePath);
			}catch (Exception ex) {
				System.out.print(ex.getMessage());
			}
			//delete product
			repo.delete(gallery);
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
		Gallery gallery  = repo.findById(id).get();
		model.addAttribute("slide", gallery);	
		return "/admin/gallery/edit";
	}
	
	
	  
	@PostMapping("/edit")
	public String editSlide(Model model, @RequestParam Integer id, @ModelAttribute Gallery gallery_form, 
			@RequestParam("file") MultipartFile image) {
		Gallery curent_gallery = repo.findById(id).get();
		model.addAttribute(curent_gallery);
		Date createAt = new Date();

	//	if (!gala.getImageFileName().isBlank()) {
		if (!image.getOriginalFilename().isEmpty()) {
			//Xoa File
//			String upload_path = "public/images/";

			Path imagePath = Paths.get(upload_path + curent_gallery.getFileName());
			try {
				System.out.println("Xoa File cũ " + curent_gallery.getFileName());

				Files.delete(imagePath);
				System.out.println("Xoa File thanh cong: " );

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
			
			storageService.saveFile(upload_path, image, storageFileName);

			curent_gallery.setFileName(storageFileName);

		}else {
			System.out.println("Khong sua File: " );
		}
		
		curent_gallery.setTitle(gallery_form.getTitle());
//		curent_gallery.setComment(gallery_form.getComment());

		curent_gallery.setUpdateAt(createAt);
		curent_gallery.setType(gallery_form.getType());
//		curent_slide.setContent(blog_form.getContent());
		curent_gallery.setStatus(gallery_form.getStatus());

		repo.save(curent_gallery);
		return "redirect:list";
		
		
	  }
}
