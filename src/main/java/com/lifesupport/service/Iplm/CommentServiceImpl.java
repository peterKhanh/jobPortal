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

import com.lifesupport.models.Comment;
import com.lifesupport.repository.CategoryRepository;
import com.lifesupport.repository.CommentRepository;
import com.lifesupport.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository repository;

	
	 // Lấy giá trị config từ file application.properties
  @Value("${number_of_item_perpage_backend}")
  int number_of_item_perpage;

	
//	@Override
//	public List<Slide> getAll() {
//		return repository.findAll();
//	}

	@Override
	public Boolean create(Comment comment) {
		try {
			this.repository.save(comment);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Comment find(Integer id) {
		return this.repository.findById(id).get();
	}

	@Override
	public Boolean update(Comment comment) {
		try {
			this.repository.save(comment);
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


	

}
