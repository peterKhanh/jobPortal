package com.lifesupport.service;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.lifesupport.models.User;
import com.lifesupport.ConstantsClass;
import com.lifesupport.models.Role;
import com.lifesupport.models.UserRole;
import com.lifesupport.repository.RoleRepository;
import com.lifesupport.repository.UserRepository;
import com.lifesupport.repository.UserRoleRepository;



@Service
public class UserServiceIpml implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private UserRoleRepository repoUserRole;
	@Autowired 
	private RoleRepository repoRole;
	
//	@Autowired
//	private BCryptPasswordEncoder encodeM;

	   int number_of_item_perpage = ConstantsClass.CONST_NUMBER_ROW_PER_PAGE_IN_USER_LIST_BACKEND;


	@Override
	public User findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(userName);
	}

	@Override
	public Boolean create(User user) {
		try {
		//	System.out.println(passwordEncoder.encode("123456"));
			this.userRepository.save(user);
	
//	    	Role roleUser = repoRole.findByName("ROLE2");
//		    repoUserRole.save(new UserRole(user,roleUser));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Long id) {
		try {
			User user = userRepository.findById(id).get();
			
			UserRole userRole = repoUserRole.findByUser(user);
			if(userRole != null) {
				repoUserRole.delete(userRole);
			}
			this.userRepository.delete(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Page<User> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage);
		//		return userRepository.findAll(pageable);
		
		return userRepository.findAll(pageable);
		
	}

	@Override
	public Page<User> searchUser(String keyword, Integer pageNo) {
		List list = this.searchUser(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<User>(list, pageable, this.searchUser(keyword).size());
	}
	
	
	@Override
	public Page<User> SearchSystemUserAndUserAdmin(String keyword, Integer pageNo) {
		List list = this.searchSystemUserAndUserAdmin(keyword);
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage);
		Integer start = (int) pageable.getOffset();
		Integer end =  	(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size(): pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<User>(list, pageable, this.searchSystemUserAndUserAdmin(keyword).size());
	}

	@Override
	public List<User> searchUser(String keyword) {
		return userRepository.SearchUser(keyword);
	}

	@Override
	public List<User> searchSystemUserAndUserAdmin(String keyword) {
		  
		Role roleSystemUser = repoRole.findByName("ADMIN"); 
		Role roleAdminUser = repoRole.findByName("ADMIN"); 
		Long roleIdSystemUser = roleSystemUser.getId();
		Long roleIdAdminUser = roleAdminUser.getId();
		return userRepository.SearchSystemUserAndUserAdmin(keyword, roleIdSystemUser, roleIdAdminUser);
	}

	
	
	@Override
	public Boolean checkLogin(Model model, Principal principal) {
		Boolean logged = false;
		String LoggedIn;

		if(principal != null) {
			String userName = principal.getName();
			User loggedUser = this.findByUserName(userName);
			
			System.out.println("Username is: " + userName);
			System.out.println("FullName is: " + loggedUser.getFullName());

			model.addAttribute("loggedUser", loggedUser);
			LoggedIn = "LOGIN";
			logged = true;		      
		}else {
			logged = false;
			LoggedIn = "NOT_LOGIN";
		}
		model.addAttribute("LoggedIn", LoggedIn);
		return logged;
	}

	@Override
	public Page<User> getAllCandidate(Integer pageNo) {
		
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage);
		  
		  Role role = repoRole.findByName("CANDIDATE"); 
		 // UserRole userRole = repoUserRole.findByRole(roleUser);
		 Long roleId = role.getId();
		 return userRepository.findByUserRoles(pageable, roleId);
	}

	@Override
	public	Page<User> getApplyCandidateByEnterprise(Integer pageNo, Long enterpriseId){
		
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage);
		  
		return userRepository.GetAllCandidateApplyToEnterprise(pageable, enterpriseId);
	}





	@Override
	public Page<User> getApplyCandidate(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, 10);
		return userRepository.findRecentApplyCandidate(pageable);
	}

	@Override
	public Page<User> getAllSystemAdminAndUserAdmin(Integer pageNo) {
		
		Pageable pageable = PageRequest.of(pageNo-1, number_of_item_perpage);
		  
		Role roleSystemUser = repoRole.findByName("ADMIN"); 
		Role roleAdminUser = repoRole.findByName("ADMIN"); 
		// UserRole userRole = repoUserRole.findByRole(roleUser);
		Long roleIdSystemUser = roleSystemUser.getId();
		Long roleIdAdminUser = roleAdminUser.getId();
		
		return userRepository.findAdminUserAndSystemAdmin(pageable, roleIdSystemUser, roleIdAdminUser);
	}
}




