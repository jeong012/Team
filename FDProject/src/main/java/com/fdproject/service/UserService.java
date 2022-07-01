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
	UserDTO findByUser(OAuth2UserDTO oAuth2UserDTO);

	/** ID 중복체크*/
	int idCheck(String userId);
}
