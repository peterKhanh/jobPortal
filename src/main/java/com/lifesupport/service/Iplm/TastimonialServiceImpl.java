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

import com.lifesupport.models.Tastimonial;
import com.lifesupport.repository.TastimonialRepository;
import com.lifesupport.service.TastimonialService;

@Service
public class TastimonialServiceImpl implements TastimonialService {
	@Autowired
	private TastimonialRepository repository;

	
	 // Lấy giá trị config từ file application.properties
  @Value("${number_of_item_perpage_backend}")
  int number_of_item_perpage;

	
	@Override
	public List<Tastimonial> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(Tastimonial tastimonial) {
		try {
			this.repository.save(tastimonial);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Tastimonial find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(Tastimonial category) {
		try {
			this.repository.save(category);
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
	public List<Tastimonial> searchTastimonial(String keyword) {
		return repository.SearchTastimonial(keyword);
	}

	@Override
	public Page<Tastimonial> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage, Sort.by("updateAt").descending());
		return repository.findAll(pageable);
	}

	@Override
	public Page<Tastimonial> searchTastimonial(String keyword, Integer pageNo) {
		List list = this.searchTastimonial(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Tastimonial>(list, pageable, this.searchTastimonial(keyword).size());
	}

}
