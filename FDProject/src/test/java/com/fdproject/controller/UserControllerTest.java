package com.fdproject.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fdproject.domain.OAuth2UserDTO;
import com.fdproject.domain.UserDTO;
import com.fdproject.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public int createUser(String userId, String pw) {
		UserDTO userDto = new UserDTO();
		String encodePw = passwordEncoder.encode(pw);
		userDto.setUserId(userId);
		userDto.setPw(encodePw);
		userDto.setName("홍길동");
		userDto.setPhoneNumber("010-1111-1111");
		userDto.setSex("남자");
		userDto.setBirthDate("2022-07-04");
		return userService.joinUser(userDto, null, null);
	}
	
	@Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception{
		UserDTO UserDTO = new UserDTO();
		UserDTO.setUserId("userTest1");
		UserDTO.setPw("userTest1");
		UserDTO.setRegistrationId("main");
		
        mockMvc.perform(formLogin().userParameter("userId")
                .loginProcessingUrl("/user/loginForm.do")
                .user(UserDTO.getUserId()).password(UserDTO.getPassword()))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }
//	
//	@Test
//    @DisplayName("로그인 실패 테스트")
//    public void loginFailTest() throws Exception{
//		String userId = "test1";
//        String pw = "12345";
//        this.saveUser(userId, pw);
//        mockMvc.perform(formLogin().userParameter("userId")
//                .loginProcessingUrl("/user/loginForm.do")
//                .user(userId).password(pw))
//                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
//    }
	
}
