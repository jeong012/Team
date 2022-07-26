package com.fdproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Test
	public void findByIdTest() {
		String userId = "test22";
		
		int userDto = userService.findById(userId);
		System.out.println(userDto);
	}
	
//	@Test
//	@DisplayName("중복 회원 가입 테스트")
//	public void saveDuplicateUserTest() {
//		
//	}
}
