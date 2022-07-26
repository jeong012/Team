package com.fdproject.service;

import java.util.ArrayList;
import java.util.List;

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
	int updateUser(UserDTO userDTO, ArrayList<UserDiseaseDTO> userDiseaseList, ArrayList<UserDrugDTO> userDrugList);
	
	/** 회원 정보 수정 - 비밀번호 확인 */
	int checkPw(String id, String pw);
	
	/** 회원 리스트 */
	List<UserDTO> getUserList(UserDTO params);
	
	/** 회원 정보 가져오기 */
	UserDTO getUserDetail(int userNo);
	
	/** 회원 삭제 */
	int deleteUser(int userNo);
	
}
