package com.lifesupport.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lifesupport.models.JobCategory;
import com.lifesupport.service.JobCategoryService;

@Controller
@RequestMapping("/admin")

public class JobCategoryController {
	@Autowired
	private JobCategoryService categoryService;

	@GetMapping("/jobcategory")
	public String index(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") Integer pageNo) {
		Page<JobCategory> listCate = categoryService.getAll(pageNo);
		
//		List<Category> listCate = categoryService.getAll();
		
		if (keyword != null) {
			listCate = categoryService.searchCate(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", listCate.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("listCate", listCate);
		return "admin/jobcategory/index";
	}

	@GetMapping("/add-jobcategory")
	public String addCate(Model model) {
		JobCategory category = new JobCategory();
		category.setStatus(true);
		model.addAttribute("category", category);
		return "admin/jobcategory/add";
	}

	@PostMapping("/add-jobcategory")
	public String save(@ModelAttribute JobCategory category) {

		if (this.categoryService.create(category)) {
			return "redirect:/admin/jobcategory";
		} else {
			return "jobcategory/add";

		}
	}

	@GetMapping("/edit-jobcategory/{id}")
	public String edit(Model model, @PathVariable Integer id) {
		JobCategory category = this.categoryService.find(id);
		model.addAttribute("category", category);
		return "admin/jobcategory/edit";
	}

	@PostMapping("/edit-jobcategory")
	public String update(@ModelAttribute JobCategory category) {

		if (this.categoryService.create(category)) {
			return "redirect:/admin/jobcategory";
		} else {
			return "jobcategory/add";

		}
	}

	@GetMapping("/delete-jobcategory/{id}")
	public String delete(@PathVariable Integer id) {
		if (this.categoryService.delete(id)) {
			return "redirect:/admin/jobcategory";
		} else {
			return "redirect:/admin/jobcategory";
		}
	}
}
