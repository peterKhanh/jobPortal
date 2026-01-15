package com.lifesupport.service.Iplm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lifesupport.models.BlogCate;
import com.lifesupport.repository.BlogCateRepository;
import com.lifesupport.service.BlogCateService;

@Service
public class BlogCateServiceImpl implements BlogCateService {
	@Autowired
	private BlogCateRepository repository;

	@Override
	public List<BlogCate> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(BlogCate blogCate ) {
		try {
			this.repository.save(blogCate);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public BlogCate find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(BlogCate blogCate) {
		try {
			this.repository.save(blogCate);
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
	public List<BlogCate> getAllActiveBlogCate() {
		// TODO Auto-generated method stub
		return repository.findAllByActive();
	}

	@Override
	public List<BlogCate> searchBlogCate(String keyword) {
		return repository.SearchBlogCate(keyword);
	}

	@Override
	public Page<BlogCate> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return repository.findAll(pageable);
	}

	@Override
	public Page<BlogCate> searchBlogCate(String keyword, Integer pageNo) {
		List list = this.searchBlogCate(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<BlogCate>(list, pageable, this.searchBlogCate(keyword).size());
	}

}
