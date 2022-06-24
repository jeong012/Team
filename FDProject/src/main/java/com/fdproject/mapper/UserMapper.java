package com.fdproject.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;

@Mapper
public interface UserMapper {

	/** 회원가입 - 사용자 지병 데이터 추가*/
    int insertUserDisease(UserDiseaseDTO userDiseaseDTO);
    
    /** 회원가입 - 사용자 복용중인 약 데이터 추가*/
    int insertUserDrug(UserDrugDTO userDrugDTO);
	
}
