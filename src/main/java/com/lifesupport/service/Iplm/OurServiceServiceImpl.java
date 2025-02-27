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

import com.lifesupport.models.OurService;
import com.lifesupport.repository.OurServiceRepository;
import com.lifesupport.service.OurServiceService;



@Service
public class OurServiceServiceImpl implements OurServiceService {
	@Autowired
	private OurServiceRepository repository;

	
	 // Lấy giá trị config từ file application.properties
  @Value("${number_of_item_perpage_backend}")
  int number_of_item_perpage;

	
	@Override
	public List<OurService> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(OurService ourService) {
		try {
			this.repository.save(ourService);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public OurService find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(OurService ourService) {
		try {
			this.repository.save(ourService);
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
	public List<OurService> searchOurService(String keyword) {
		return repository.SearchOurService(keyword);
	}

	@Override
	public Page<OurService> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage, Sort.by("updateAt").descending());
		return repository.findAll(pageable);
	}

	@Override
	public Page<OurService> searchOurService(String keyword, Integer pageNo) {
		List list = this.searchOurService(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<OurService>(list, pageable, this.searchOurService(keyword).size());
	}

}
