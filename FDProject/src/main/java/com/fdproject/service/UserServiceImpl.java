package com.fdproject.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdproject.domain.OAuth2UserDTO;
import com.fdproject.domain.UserDTO;
import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;
import com.fdproject.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserMapper userMapper;
    
	@Override
    @Transactional
	public int joinUser(UserDTO userDto, ArrayList<UserDiseaseDTO> userDiseaseList, ArrayList<UserDrugDTO> userDrugList) {
    	int isInserted = 0;
    	
    	/** 회원가입 - 사용자 정보 추가*/
    	userDto.setRole("USER");
    	System.out.println("userDto: " + userDto);
    	isInserted = userMapper.saveUser(userDto);
    	System.out.println("isInserted: " + isInserted);
    	
    	/** 회원가입 - 사용자 지병 데이터 추가*/
    	for (UserDiseaseDTO userDiseaseDTO : userDiseaseList) {
            isInserted = userMapper.insertUserDisease(userDiseaseDTO);
    	}

        /** 회원가입 - 사용자 복용중인 약 데이터 추가*/
    	for (UserDrugDTO userDrugDTO : userDrugList) {
	        isInserted = userMapper.insertUserDrug(userDrugDTO);
    	}
    	
		return isInserted;
	}

	/** OAuth2 기존 회원 여부 조회*/
	@Override
	public UserDTO findByUser(OAuth2UserDTO oAuth2UserDTO) {
		UserDTO user = userMapper.findByUser(oAuth2UserDTO);
		return user;
	}

	/** ID 중복체크*/
	@Override
	public int findById(String userId) {
		int cnt = userMapper.findById(userId);
		return cnt;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//		UserDTO userDto = userMapper.findById(userId);
//		
//		if(userDto == null) {
//			throw new UsernameNotFoundException("userId" + userId + "not found");
//		}
//		System.out.println("**************Found user***************");
//		System.out.println("id : " + userDto.getUserId());
//		
//		return User.builder()
//				.username(userDto.getUserId())
//				.password(userDto.getPw())
//				.roles(userDto.getRole().toString())
//				.build();
//	}
}
