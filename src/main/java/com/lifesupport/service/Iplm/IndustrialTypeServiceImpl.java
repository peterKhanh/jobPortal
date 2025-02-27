package com.lifesupport.service.Iplm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lifesupport.models.IndustrialType;
import com.lifesupport.repository.IndustrialTypeRepository;
import com.lifesupport.service.IndustrialTypeService;

@Service
public class IndustrialTypeServiceImpl implements IndustrialTypeService {
	@Autowired
	private IndustrialTypeRepository repository;

	@Override
	public List<IndustrialType> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(IndustrialType industrialType) {
		try {
			this.repository.save(industrialType);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public IndustrialType find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(IndustrialType  industrialType) {
		try {
			this.repository.save(industrialType);
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
	public List<IndustrialType> getAllActiveIndustrialType() {
		// TODO Auto-generated method stub
		return repository.findAllByActive();
	}

	@Override
	public List<IndustrialType> searchIndustrialType(String keyword) {
		return repository.SearchIndustrialType(keyword);
	}

	@Override
	public Page<IndustrialType> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return repository.findAll(pageable);
	}

	@Override
	public Page<IndustrialType> searchCate(String keyword, Integer pageNo) {
		List list = this.searchIndustrialType(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<IndustrialType>(list, pageable, this.searchIndustrialType(keyword).size());
	}

}
