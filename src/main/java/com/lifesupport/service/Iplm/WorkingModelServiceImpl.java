package com.lifesupport.service.Iplm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lifesupport.models.WorkingModel;
import com.lifesupport.repository.WorkingModelRepository;
import com.lifesupport.service.WorkingModelService;

@Service
public class WorkingModelServiceImpl implements WorkingModelService {
	@Autowired
	private WorkingModelRepository repository;

	@Override
	public List<WorkingModel> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(WorkingModel workingmodel) {
		try {
			this.repository.save(workingmodel);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public WorkingModel find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(WorkingModel workingmodel) {
		try {
			this.repository.save(workingmodel);
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
	public List<WorkingModel> getAllActiveWorkingModel() {
		// TODO Auto-generated method stub
		return repository.findAllByActive();
	}

	@Override
	public List<WorkingModel> searchWorkingModel(String keyword) {
		return repository.SearchWorkingModel(keyword);
	}

	@Override
	public Page<WorkingModel> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return repository.findAll(pageable);
	}

	@Override
	public Page<WorkingModel> searchWorkingModel(String keyword, Integer pageNo) {
		List list = this.searchWorkingModel(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<WorkingModel>(list, pageable, this.searchWorkingModel(keyword).size());
	}



}
