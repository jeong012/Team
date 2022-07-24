package com.fdproject.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
		String encodePw = passwordEncoder.encode(userDto.getPw());
		userDto.setPw(encodePw);
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

	@Override
	public UserDTO loadUserByUsername(String userId) throws UsernameNotFoundException {
		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);
		OAuth2UserDTO oAuth2User = (OAuth2UserDTO) httpSession.getAttribute("oAuth2User");

		UserDTO principal = new UserDTO();
		if(oAuth2User == null) {
			principal = userMapper.findByUser(userId);
		} else {
			principal = userMapper.loginByOAuth2(userId, oAuth2User.getRegistrationId());
		}
		
		if(principal == null) {
			throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + userId);
		}
		return principal;
	}

	@Transactional
	public int updateUser(UserDTO userDto) {
		System.out.println("UserServiceImpl updateUser 호출!! >>>>>>>>>>>>>>>" + userDto);
		
		if(userDto.getUserId() == null) {
			throw new IllegalArgumentException("회원 찾기 실패!!");
		}
		String rawPassword = userDto.getPw();
        String encPassword = passwordEncoder.encode(rawPassword);
        userDto.setPw(encPassword);
		
		int count = userMapper.updateUser(userDto);
		
        
		return count;
	}

	/** 회원 정보 수정 - 비밀번호 확인 */
	@Override
	public int checkPw(String id, String pw) {
		System.out.println(">>>" + passwordEncoder.encode(pw));
		int count = userMapper.checkPw(id, passwordEncoder.encode(pw));
		return count;
	}
}
