package com.project.GreUserService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.GreUserService.model.AppUser;

@Repository
public interface UserDao extends JpaRepository<AppUser, Integer>{

	boolean existsByUserId(Integer userId);
	
}
