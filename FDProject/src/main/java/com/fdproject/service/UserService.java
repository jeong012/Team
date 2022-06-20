package com.fdproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdproject.domain.UserDTO;
import com.fdproject.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userMapper;

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
