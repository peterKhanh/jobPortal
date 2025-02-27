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

import com.lifesupport.models.OurService;
import com.lifesupport.repository.OurServiceRepository;
import com.lifesupport.service.FilesStorageService;
import com.lifesupport.service.OurServiceService;

@Controller
@RequestMapping("/admin/service")

public class OurServiceController {
	@Autowired
	private OurServiceRepository repo;
	@Autowired
	private OurServiceService ourServiceService;
	@Autowired
	FilesStorageService storageService;

	// Lấy giá trị config từ file application.properties
   @Value("${upload_service_path}")
   String upload_path;

	@GetMapping("/add")
	public String showAddPage(Model model) {

//		List<Slide> list = slideService.getAllActiveCate();
//		model.addAttribute("listCate", listCate);

		OurService ourService  = new OurService();
		model.addAttribute("ourService", ourService);
		return "/admin/service/add";
	}

	

	@PostMapping("/add")
	public String addSlide(Model model, @ModelAttribute OurService service_form, @RequestParam("file") MultipartFile image) {
		Date createAt = new Date();
		String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();

		storageService.saveFile(upload_path, image, storageFileName);

		OurService ourService  = new OurService();
		ourService.setTitle(service_form.getTitle());
		ourService.setComment(service_form.getComment());
		ourService.setCreateAt(createAt);
		ourService.setFileName(storageFileName);
		ourService.setStatus(service_form.getStatus());
		repo.save(ourService);
		return "redirect:list";
	}

	
	@GetMapping("/list")
	public String getAllOurServiceSearchAndPaging(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		Page<OurService> servicelist = ourServiceService.getAll(pageNo);
		if (keyword != null) {
			System.out.println("keyword : " + keyword);

			servicelist = ourServiceService.searchOurService(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", servicelist.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("servicelist", servicelist);
		return "/admin/service/list";
	}
	
	@GetMapping("/delete")
	public String deleteSlide( @RequestParam Integer id) {
		try {
			OurService ourService = repo.findById(id).get();
			Path imagePath = Paths.get(upload_path + ourService.getFileName());
			try {
				Files.delete(imagePath);
			}catch (Exception ex) {
				System.out.print(ex.getMessage());
			}
			//delete product
			repo.delete(ourService);
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
		OurService ourService  = repo.findById(id).get();
		model.addAttribute("ourService", ourService);	
		return "/admin/service/edit";
	}
	
	

	@PostMapping("/edit")
	public String editSlide(Model model, @RequestParam Integer id, @ModelAttribute OurService service_form, 
			@RequestParam("file") MultipartFile image) {
		OurService curent_service = repo.findById(id).get();
		model.addAttribute(curent_service);
		Date currentDate = new Date();

		if (!image.getOriginalFilename().isEmpty()) {
			//Xoa File

			Path imagePath = Paths.get(upload_path + curent_service.getFileName());
			try {
				System.out.println("Xoa File cũ " + curent_service.getFileName());

				Files.delete(imagePath);
				System.out.println("Xoa File thanh cong: " );

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			String storageFileName = currentDate.getTime() + "_" + image.getOriginalFilename();

			storageService.saveFile(upload_path, image, storageFileName);

			curent_service.setFileName(storageFileName);

		}else {
			System.out.println("Khong sua File: " );
		}
		
		curent_service.setTitle(service_form.getTitle());
		curent_service.setComment(service_form.getComment());

		curent_service.setUpdateAt(currentDate);
		curent_service.setStatus(service_form.getStatus());

		repo.save(curent_service);
		return "redirect:list";
		
		
	  }
}
