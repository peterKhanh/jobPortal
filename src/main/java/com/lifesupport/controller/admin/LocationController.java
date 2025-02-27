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

import com.lifesupport.models.Location;
import com.lifesupport.service.LocationService;

@Controller
@RequestMapping("/admin")

public class LocationController {
	@Autowired
	private LocationService service;

	@GetMapping("/location")
	public String index(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") Integer pageNo) {
		Page<Location> listitem = service.getAll(pageNo);
		
//		List<Category> listCate = service.getAll();
		
		if (keyword != null) {
			listitem = service.searchCate(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}
		model.addAttribute("totalPage", listitem.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("listitem", listitem);
		return "admin/location/index";
	}

	@GetMapping("/add-location")
	public String addLOcation(Model model) {
		Location item = new Location();
		item.setStatus(true);
		model.addAttribute("location", item);
		return "admin/location/add";
	}

	@PostMapping("/add-location")
	public String save(@ModelAttribute Location category) {

		if (this.service.create(category)) {
			return "redirect:/admin/location";
		} else {
			return "location/add";

		}
	}

	@GetMapping("/edit-location/{id}")
	public String edit(Model model, @PathVariable Integer id) {
		Location location = this.service.find(id);
		model.addAttribute("location", location);
		return "admin/location/edit";
	}

	@PostMapping("/edit-location")
	public String update(@ModelAttribute Location category) {

		if (this.service.create(category)) {
			return "redirect:/admin/location";
		} else {
			return "location/add";

		}
	}

	@GetMapping("/delete-location/{id}")
	public String delete(@PathVariable Integer id) {
		if (this.service.delete(id)) {
			return "redirect:/admin/location";
		} else {
			return "redirect:/admin/location";
		}
	}
}
