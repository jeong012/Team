package com.fdproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fdproject.domain.OAuth2UserDTO;
import com.fdproject.domain.UserDTO;
import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;

@Mapper
public interface UserMapper {

	/** 회원가입 - 사용자 지병 데이터 추가*/
    int insertUserDisease(UserDiseaseDTO userDiseaseDTO);
    
    /** 회원가입 - 사용자 복용중인 약 데이터 추가*/
    int insertUserDrug(UserDrugDTO userDrugDTO);

	/** OAuth2 기존 회원 여부 조회*/
	UserDTO findByOAuth2User(OAuth2UserDTO oAuth2UserDTO);

	/** ID 중복체크*/
	int findById(String userId);

	/** 회원가입 - 회원정보 저장*/
	int saveUser(UserDTO userDto);
	
	/** 로그인*/
	UserDTO findByUser(String userId);
	
	/** OAuth2 로그인*/
	UserDTO loginByOAuth2(@Param("userId") String userId, @Param("registraionId") String registraionId);
	
	/** 회원정보 수정*/
	int updateUser(UserDTO userDto);
	
	/** 회원 정보 수정 - 비밀번호 확인 */
	int checkPw(@Param("id") String id, @Param("pw") String pw);

}
