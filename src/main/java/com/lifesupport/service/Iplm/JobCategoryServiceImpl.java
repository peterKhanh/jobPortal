package com.lifesupport.service.Iplm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lifesupport.models.JobCategory;
import com.lifesupport.repository.JobCategoryRepository;
import com.lifesupport.service.JobCategoryService;

@Service
public class JobCategoryServiceImpl implements JobCategoryService {
	@Autowired
	private JobCategoryRepository repository;

	@Override
	public List<JobCategory> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(JobCategory category) {
		try {
			this.repository.save(category);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public JobCategory find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(JobCategory category) {
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
	public List<JobCategory> getAllActiveCate() {
		// TODO Auto-generated method stub
		return repository.findAllByActive();
	}

	@Override
	public List<JobCategory> searchCate(String keyword) {
		return repository.SearchJobCategory(keyword);
	}

	@Override
	public Page<JobCategory> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return repository.findAll(pageable);
	}

	@Override
	public Page<JobCategory> searchCate(String keyword, Integer pageNo) {
		List list = this.searchCate(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<JobCategory>(list, pageable, this.searchCate(keyword).size());
	}

}
