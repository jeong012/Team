package com.fdproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdproject.domain.UserDTO;
import com.fdproject.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl {
	
	private final UserMapper userMapper;
	
	public boolean existId(String userId) {
		UserDTO users = userMapper.selectUserDetail(userId);
		return users != null;
	}
	
	public void UserService() {
		System.out.println("userService 생성");
	}		

	public boolean registerUser(UserDTO users) {
		return userMapper.insertUser(users);
	}

	public UserDTO getUserDetail(Long userId) {
		return null;
	}

	public boolean deleteUser(UserDTO users) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
