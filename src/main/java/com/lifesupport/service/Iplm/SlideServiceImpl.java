package com.lifesupport.service.Iplm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lifesupport.models.Slide;
import com.lifesupport.repository.CategoryRepository;
import com.lifesupport.repository.SlideRepository;
import com.lifesupport.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService {
	@Autowired
	private SlideRepository repository;

	
	 // Lấy giá trị config từ file application.properties
  @Value("${number_of_item_perpage_backend}")
  int number_of_item_perpage;

	
	@Override
	public List<Slide> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(Slide slide) {
		try {
			this.repository.save(slide);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Slide find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(Slide slide) {
		try {
			this.repository.save(slide);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.repository.delete(find(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public List<Slide> searchSlide(String keyword) {
		return repository.SearchSlide(keyword);
	}

	@Override
	public Page<Slide> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage, Sort.by("updateAt").descending());
		return repository.findAll(pageable);
	}

	@Override
	public Page<Slide> searchSlide(String keyword, Integer pageNo) {
		List list = this.searchSlide(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Slide>(list, pageable, this.searchSlide(keyword).size());
	}

}
