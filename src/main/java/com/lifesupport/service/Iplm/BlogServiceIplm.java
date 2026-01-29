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

import com.lifesupport.ConstantsClass;
import com.lifesupport.models.Blog;
import com.lifesupport.models.Category;
import com.lifesupport.models.BlogCate;
import com.lifesupport.repository.BlogRepository;
import com.lifesupport.repository.CategoryRepository;
import com.lifesupport.repository.BlogCateRepository;
import com.lifesupport.service.BlogService;
@Service
public class BlogServiceIplm implements BlogService {
	@Autowired
	private BlogRepository repository;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private BlogCateRepository blogcateRepo;
	
	// Lấy giá trị config từ file application.properties
	int number_of_item_perpage = ConstantsClass.CONST_NUMBER_JOB_PER_PAGE_IN_FRONTEND;

	
	@Override
	public Page<Blog> getAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage, Sort.by("createAt").descending());
		return repository.findAll(pageable);
	}

	@Override
	public Page<Blog> searchBlog(String keyword, Integer pageNo) {
		List list = this.searchBlog(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage, Sort.by("updateAt").descending());
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Blog>(list, pageable, this.searchBlog(keyword).size());
	}

	@Override
	public List<Blog> searchBlog(String keyword) {
		// TODO Auto-generated method stub
		return repository.SearchBlog(keyword);
	}

	@Override
	public Page<Blog> getAllByCate(Integer pageNo, Integer cateId) {
		System.out.println("Cate ID = " + cateId);
		Category category = categoryRepo.findById(cateId).get();
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage, Sort.by("createAt").descending());
//		List list = repository.findByCategory(category, pageable);
		Page page = repository.findByCategory(category, pageable);
//		Integer start = (int) pageable.getOffset();
//		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		System.out.println("Số trang: :" + page.getSize());
//		list = list.subList(start, end);
		return page;
//		return new PageImpl<Blog>(list, pageable, list.size());
	}

	@Override
	public Page<Blog> getAllByBlogCate(Integer pageNo, Integer blogCateId) {
		System.out.println("Cate ID = " + blogCateId);
		BlogCate blogcate = blogcateRepo.findById(blogCateId).get();

		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage, Sort.by("createAt").descending());
//		List list = repository.findByCategory(category, pageable);
		Page page = repository.findByBlogcate(blogcate, pageable);
//		Integer start = (int) pageable.getOffset();
//		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		System.out.println("Số trang: :" + page.getSize());
//		list = list.subList(start, end);
		return page;
//		return new PageImpl<Blog>(list, pageable, list.size());
	}

}
