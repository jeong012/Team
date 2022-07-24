package com.fdproject.service;

import java.util.ArrayList;

import com.fdproject.domain.OAuth2UserDTO;
import com.fdproject.domain.UserDTO;
import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;

public interface UserService {

	/** 회원가입 - 사용자 정보 + 사용자 지병 + 복용중인 약 데이터 추가*/
	int joinUser(UserDTO userDto, ArrayList<UserDiseaseDTO> userDiseaseList, ArrayList<UserDrugDTO> userDrugList);

	/** OAuth2 기존 회원 여부 조회*/
	UserDTO findByOAuth2User(OAuth2UserDTO oAuth2UserDTO);

	/** ID 중복체크*/
	int findById(String userId);
	
	/** 회원 정보 수정*/
	int updateUser(UserDTO userDTO);
	
	/** 회원 정보 수정 - 비밀번호 확인 */
	int checkPw(String id, String pw);

	UserDTO loadUserByUsername(String userId);
	
}
