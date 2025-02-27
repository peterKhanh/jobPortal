package com.lifesupport.service.Iplm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lifesupport.models.Location;
import com.lifesupport.repository.LocationRepository;
import com.lifesupport.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	@Autowired
	private LocationRepository repository;

	@Override
	public List<Location> getAll() {
		return repository.findAll();
	}

	@Override
	public Boolean create(Location location) {
		try {
			this.repository.save(location);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Location find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(Location location) {
		try {
			this.repository.save(location);
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
	public List<Location> getAllActiveLocation() {
		// TODO Auto-generated method stub
		return repository.findAllByActive();
	}

	@Override
	public List<Location> searchLocation(String keyword) {
		return repository.SearchLocation(keyword);
	}

	@Override
	public Page<Location> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return repository.findAll(pageable);
	}

	@Override
	public Page<Location> searchCate(String keyword, Integer pageNo) {
		List list = this.searchLocation(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Location>(list, pageable, this.searchLocation(keyword).size());
	}

}
