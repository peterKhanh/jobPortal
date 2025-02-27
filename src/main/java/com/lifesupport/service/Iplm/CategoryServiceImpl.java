package com.lifesupport.service.Iplm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lifesupport.models.Category;
import com.lifesupport.repository.CategoryRepository;
import com.lifesupport.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository repository;

	@Override
	public List<Category> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(Category category) {
		try {
			this.repository.save(category);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Category find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(Category category) {
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
	public List<Category> getAllActiveCate() {
		// TODO Auto-generated method stub
		return repository.findAllByActive();
	}

	@Override
	public List<Category> searchCate(String keyword) {
		return repository.SearchCategory(keyword);
	}

	@Override
	public Page<Category> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return repository.findAll(pageable);
	}

	@Override
	public Page<Category> searchCate(String keyword, Integer pageNo) {
		List list = this.searchCate(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Category>(list, pageable, this.searchCate(keyword).size());
	}

}
