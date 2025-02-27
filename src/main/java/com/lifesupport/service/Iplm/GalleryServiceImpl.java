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

import com.lifesupport.models.Blog;
import com.lifesupport.models.Category;
import com.lifesupport.models.Gallery;
import com.lifesupport.models.Slide;
import com.lifesupport.repository.CategoryRepository;
import com.lifesupport.repository.GalleryRepository;
import com.lifesupport.service.GalleryService;

@Service
public class GalleryServiceImpl implements GalleryService {
	@Autowired
	private GalleryRepository repository;

	// Lấy giá trị config từ file application.properties
	@Value("${number_of_item_perpage_backend}")
	int number_of_item_perpage;

	@Override
	public List<Gallery> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(Gallery gallery) {
		try {
			this.repository.save(gallery);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Gallery find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(Gallery gallery) {
		try {
			this.repository.save(gallery);
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
	public List<Gallery> searchGallery(String keyword) {
		return repository.SearchGallery(keyword);
	}

	@Override
	public Page<Gallery> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo - 1, number_of_item_perpage, Sort.by("updateAt").descending());
		return repository.findAll(pageable);
	}

	@Override
	public Page<Gallery> searchGallery(String keyword, Integer pageNo) {
		List list = this.searchGallery(keyword);
		Pageable pageable = PageRequest.of(pageNo - 1, number_of_item_perpage);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Gallery>(list, pageable, this.searchGallery(keyword).size());
	}

	@Override
	public Page<Gallery> getAllByType(Integer pageNo, String type) {
		System.out.println("Type gallery = " + type);
//		Category category = categoryRepo.findById(cateId).get();
//		List list = repository.SearchByCategory(category);
		List list = repository.SearchByType(type);
		Pageable pageable = PageRequest.of(pageNo - 1, 4);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		System.out.println("Size = " + list.size());
		return new PageImpl<Gallery>(list, pageable, list.size());
	}
	
	@Override
	public Page<Gallery> getByType(Integer pageNo, String type) {
		List list = repository.SearchByType(type);
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Gallery>(list, pageable, list.size());

	}


}
