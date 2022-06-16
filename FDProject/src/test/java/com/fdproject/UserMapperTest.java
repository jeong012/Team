package com.fdproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdproject.domain.UserDTO;
import com.fdproject.mapper.UserMapper;

@SpringBootTest
class UserMapperTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testOfInsert() {
		UserDTO users = new UserDTO();
		users.setUserId("userTest1");
		users.setPw("1234");
		users.setName("userTest1");
		users.setPhoneNumber("01011111111");
		users.setSex("");
		users.setBirthDate(null);
		
		int result = userMapper.insertUser(users);
	    System.out.println("결과는 " + result + "입니다.");
	}
	
}
