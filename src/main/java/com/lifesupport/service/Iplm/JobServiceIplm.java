package com.lifesupport.service.Iplm;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lifesupport.ConstantsClass;
import com.lifesupport.models.JobCategory;
import com.lifesupport.models.Location;
import com.lifesupport.models.Enterprise;
import com.lifesupport.models.Job;
import com.lifesupport.repository.JobCategoryRepository;
import com.lifesupport.repository.JobRepository;
import com.lifesupport.repository.LocationRepository;
import com.lifesupport.service.JobService;
@Service
public class JobServiceIplm implements JobService {
	@Autowired
	private JobRepository repository;
	@Autowired
	private JobCategoryRepository categoryRepo;
	@Autowired
	private LocationRepository locationRepo;
	
// Lấy giá trị config từ file application.properties
   int number_of_item_perpage = ConstantsClass.CONST_NUMBER_JOB_PER_PAGE_IN_FRONTEND;

	@Override
	public Page<Job> getAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, number_of_item_perpage, Sort.by("createAt").descending());
		return repository.findAll(pageable);
	}

	@Override
	public List<Job> searchJob(String keyword) {
		// TODO Auto-generated method stub
		return repository.SearchJob(keyword);
	}

	@Override
	public Page<Job> searchJob(String keyword, Integer pageNo) {
		List list = this.searchJob(keyword);
		Pageable pageable = PageRequest.of(pageNo - 1, number_of_item_perpage, Sort.by("updateAt").descending());
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Job>(list, pageable, this.searchJob(keyword).size());
	}

	@Override
	public Page<Job> getApprovedJobByCategory(Integer pageNo, Integer cateId) {
		System.out.println("Cate ID = " + cateId);
		JobCategory jobcategory = categoryRepo.findById(cateId).get();

		Pageable pageable = PageRequest.of(pageNo - 1, number_of_item_perpage, Sort.by("createAt").descending());
		Page page = repository.findApprovedJobByCategory(jobcategory, pageable);
//		Integer start = (int) pageable.getOffset();
//		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		System.out.println("Số trang: :" + page.getSize());
//		list = list.subList(start, end);
		return page;
	}

	

	@Override
	public List<Job> GetAllActiveJobByEnterprise(Enterprise enterprise) {
		return repository.GetAllActiveJobByEnterprise(enterprise);
	}

	@Override
	public Optional<Job> getSingleJob(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public Page<Job> getAllJobForHomePage(Integer pageNo) {
		
		Pageable pageable = PageRequest.of(pageNo - 1, number_of_item_perpage, Sort.by("createAt").descending());
		Page page = repository.findAllJobForHomePage(pageable);
//		Integer start = (int) pageable.getOffset();
//		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		System.out.println("Số trang: :" + page.getSize());
//		list = list.subList(start, end);
		return page;
	}

	@Override
	public Page<Job> getAllByLocation(Integer pageNo, Integer locationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Job> getApprovedJobByLocation(Integer pageNo, Integer locationId) {
		System.out.println("Cate ID = " + locationId);
		Location location = locationRepo.findById(locationId).get();

		Pageable pageable = PageRequest.of(pageNo - 1, number_of_item_perpage, Sort.by("createAt").descending());
		Page page = repository.findApprovedJobByLocation(location, pageable);
//		Integer start = (int) pageable.getOffset();
//		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		System.out.println("Số trang: :" + page.getSize());
//		list = list.subList(start, end);
		return page;
	}

	
	

}
