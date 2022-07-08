package com.fdproject.mappers;

import org.junit.jupiter.api.DisplayName;
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
	@DisplayName(value = "회원정보 저장 테스트")
	public void saveTest() {
		UserDTO userDto = new UserDTO();
		userDto.setUserId("userTest7");
		userDto.setPw("userTest7");
		userDto.setName("userTest7");
		userDto.setPhoneNumber("010-1111-1111");
		userDto.setSex("남자");
		userDto.setBirthDate("2022-06-22");
		userDto.setRegistrationId("main");
		userDto.setRole("USER");
		
		int savedUsers = userMapper.saveUser(userDto);
		System.out.println("userDto: " + userDto);
//	    System.out.println(savedUsers.toString());
	    System.out.println(savedUsers);
	}
	
}
