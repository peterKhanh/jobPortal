package com.lifesupport.service;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import com.lifesupport.models.User;

public interface UserService {
	User findByUserName(String userName);
	public Boolean create(User user);
	public Boolean delete(Long id);

	
	public List<User> searchUser(String keyword);

	
	//Paging
	Page<User> getAll(Integer pageNo);
	Page<User> getAllSystemAdminAndUserAdmin(Integer pageNo);
	Page<User> getAllCandidate(Integer pageNo);

	Page<User> getApplyCandidate(Integer pageNo);

	Page<User> getApplyCandidateByEnterprise(Integer pageNo, Long enterpriseId);

	Page<User>  searchUser(String keyword, Integer pageNo);
	Page<User>  SearchSystemUserAndUserAdmin(String keyword, Integer pageNo);

	// Check Login : 
	public Boolean checkLogin(Model model,Principal principal);
	List<User> searchSystemUserAndUserAdmin(String keyword);


}
