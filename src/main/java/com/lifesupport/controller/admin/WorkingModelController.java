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

import com.lifesupport.models.WorkingModel;
import com.lifesupport.service.WorkingModelService;

@Controller
@RequestMapping("/admin")

public class WorkingModelController {
	@Autowired
	private WorkingModelService service;

	@GetMapping("/workingmodel")
	public String index(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") Integer pageNo) {
		Page<WorkingModel> listitem = service.getAll(pageNo);
		
//		List<Category> listCate = service.getAll();
		
		if (keyword != null) {
			listitem = service.searchWorkingModel(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", listitem.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("listitem", listitem);
		return "admin/workingmodel/index";
	}

	@GetMapping("/add-workingmodel")
	public String addworkingmodel(Model model) {
		WorkingModel item = new WorkingModel();
		item.setStatus(true);
		model.addAttribute("workingmodel", item);
		return "admin/workingmodel/add";
	}

	@PostMapping("/add-workingmodel")
	public String save(@ModelAttribute WorkingModel workingmodel) {

		if (this.service.create(workingmodel)) {
			return "redirect:/admin/workingmodel";
		} else {
			return "workingmodel/add";

		}
	}

	@GetMapping("/edit-workingmodel/{id}")
	public String edit(Model model, @PathVariable Integer id) {
		WorkingModel workingmodel = this.service.find(id);
		model.addAttribute("workingmodel", workingmodel);
		return "admin/workingmodel/edit";
	}

	@PostMapping("/edit-workingmodel")
	public String update(@ModelAttribute WorkingModel workingmodel) {

		if (this.service.create(workingmodel)) {
			return "redirect:/admin/workingmodel";
		} else {
			return "workingmodel/add";

		}
	}

	@GetMapping("/delete-workingmodel/{id}")
	public String delete(@PathVariable Integer id) {
		if (this.service.delete(id)) {
			return "redirect:/admin/workingmodel";
		} else {
			return "redirect:/admin/workingmodel";
		}
	}
}
