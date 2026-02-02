package com.lifesupport.service.Iplm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lifesupport.ConstantsClass;
import com.lifesupport.models.Enterprise;
import com.lifesupport.repository.EnterpriseRepository;
import com.lifesupport.service.EnterService;
@Service
public class EnterServiceIplm implements EnterService {
	@Autowired
	private EnterpriseRepository repository;

	int number_of_item_perpage_backend = ConstantsClass.CONST_NUMBER_ROW_PER_PAGE_IN_ENTERPRISE_LIST_BACKEND;

	int number_of_item_perpage_frontend = ConstantsClass.CONST_NUMBER_ROW_PER_PAGE_IN_ENTERPRISE_LIST_FRONTEND;


@Override
public List<Enterprise> searchEnterprise(String keyword) {
	// TODO Auto-generated method stub
	return repository.SearchEnterprise(keyword);
}

@Override
public Page<Enterprise> getAll(Integer pageNo) {
	Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage_backend, Sort.by("createAt").descending());
	return repository.findAll(pageable);
}

@Override
public Page<Enterprise> searchEnterprise(String keyword, Integer pageNo) {
	List list = this.searchEnterprise(keyword);
	Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage_backend, Sort.by("updateAt").descending());
	Integer start = (int) pageable.getOffset();
	Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
	list = list.subList(start, end);
	return new PageImpl<Enterprise>(list, pageable, this.searchEnterprise(keyword).size());
}

@Override
public Page<Enterprise> getAllByType(Integer pageNo, Integer industrialTypeId) {
	// TODO Auto-generated method stub
	return null;
}

// getEnterpriseList	
@Override
public Page<Enterprise> getEnterpriseList(Integer pageNo) {
	Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage_frontend, Sort.by("createAt").descending());
	return repository.findAll(pageable);
}

}
