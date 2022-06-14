package com.fdproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdproject.domain.UserDTO;
import com.fdproject.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean registerUser(UserDTO users) {
		// TODO Auto-generated method stub
		int queryResult = 0;
		
		if (users.getUserId() == null) {
			queryResult = userMapper.insertUser(users);
		}else {
			queryResult = userMapper.insertUser(users);
		}
		return (queryResult == 1) ? true : false;
	}

	@Override
	public UserDTO getFindById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUser(UserDTO users) {
		// TODO Auto-generated method stub
		return false;
	}

}
