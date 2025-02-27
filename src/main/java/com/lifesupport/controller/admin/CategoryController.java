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

import com.lifesupport.models.Category;
import com.lifesupport.service.CategoryService;

@Controller
@RequestMapping("/admin")

public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/category")
	public String index(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") Integer pageNo) {
		Page<Category> listCate = categoryService.getAll(pageNo);
		
//		List<Category> listCate = categoryService.getAll();
		
		if (keyword != null) {
			listCate = categoryService.searchCate(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", listCate.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("listCate", listCate);
		return "admin/category/index";
	}

	@GetMapping("/add-category")
	public String addCate(Model model) {
		Category category = new Category();
		category.setCategoryStatus(true);
		model.addAttribute("category", category);
		return "admin/category/add";
	}

	@PostMapping("/add-category")
	public String save(@ModelAttribute Category category) {

		if (this.categoryService.create(category)) {
			return "redirect:/admin/category";
		} else {
			return "category/add";

		}
	}

	@GetMapping("/edit-category/{id}")
	public String edit(Model model, @PathVariable Integer id) {
		Category category = this.categoryService.find(id);
		model.addAttribute("category", category);
		return "admin/category/edit";
	}

	@PostMapping("/edit-category")
	public String update(@ModelAttribute Category category) {

		if (this.categoryService.create(category)) {
			return "redirect:/admin/category";
		} else {
			return "category/add";

		}
	}

	@GetMapping("/delete-category/{id}")
	public String delete(@PathVariable Integer id) {
		if (this.categoryService.delete(id)) {
			return "redirect:/admin/category";
		} else {
			return "redirect:/admin/category";
		}
	}
}
