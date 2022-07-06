package com.fdproject.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    
	@Override
    @Transactional
	public int joinUser(UserDTO userDto, ArrayList<UserDiseaseDTO> userDiseaseList, ArrayList<UserDrugDTO> userDrugList) {
    	int isInserted = 0;
    	
    	/** 회원가입 - 사용자 정보 추가*/
    	if(userDto.getRegistrationId().equals("main")) {
    		String encodePw = passwordEncoder.encode(userDto.getPw());
    		userDto.setPw(encodePw);
    	}
    	isInserted = userMapper.saveUser(userDto);
    	
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
	public UserDTO findByOAuth2User(OAuth2UserDTO oAuth2UserDTO) {
		UserDTO user = userMapper.findByOAuth2User(oAuth2UserDTO);
		return user;
	}

	/** ID 중복체크*/
	@Override
	public int findById(String userId) {
		int cnt = userMapper.findById(userId);
		return cnt;
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public UserDTO loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserDTO authUser = userMapper.findByUser(userId);
		
		if(authUser == null) {
			throw new UsernameNotFoundException("userId" + userId + "not found");
		}
		System.out.println("**************Found user***************");
		System.out.println("userDto : " + authUser);
		System.out.println("id : " + authUser.getUserId());
		System.out.println("pw : " + authUser.getPw());
		
		return authUser;
	}
}
