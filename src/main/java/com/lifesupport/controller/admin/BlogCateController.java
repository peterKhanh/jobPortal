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

import com.lifesupport.models.BlogCate;
import com.lifesupport.service.BlogCateService;

@Controller
@RequestMapping("/admin")

public class BlogCateController {
	@Autowired
	private BlogCateService service;

	@GetMapping("/blogcate")
	public String index(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") Integer pageNo) {
		Page<BlogCate> listitem = service.getAll(pageNo);
		
		if (keyword != null) {
			listitem = service.searchBlogCate(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", listitem.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("listitem", listitem);
		return "admin/blogcate/index";
	}

	@GetMapping("/add-blogcate")
	public String addLOcation(Model model) {
		BlogCate item = new BlogCate();
		item.setStatus(true);
		model.addAttribute("location", item);
		return "admin/blogcate/add";
	}

	@PostMapping("/add-blogcate")
	public String save(@ModelAttribute BlogCate category) {

		if (this.service.create(category)) {
			return "redirect:/admin/blogcate";
		} else {
			return "blogcate/add";

		}
	}

	@GetMapping("/edit-blogcate/{id}")
	public String edit(Model model, @PathVariable Integer id) {
		BlogCate blogcate = this.service.find(id);
		model.addAttribute("blogcate", blogcate);
		return "admin/blogcate/edit";
	}

	@PostMapping("/edit-blogcate")
	public String update(@ModelAttribute BlogCate category) {

		if (this.service.create(category)) {
			return "redirect:/admin/blogcate";
		} else {
			return "blogcate/add";

		}
	}

	@GetMapping("/delete-blogcate/{id}")
	public String delete(@PathVariable Integer id) {
		if (this.service.delete(id)) {
			return "redirect:/admin/blogcate";
		} else {
			return "redirect:/admin/blogcate";
		}
	}
}
