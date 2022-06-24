package com.fdproject.service;

import java.util.ArrayList;

import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;

public interface UserService {

	/** 회원가입 - 사용자 지병 + 복용중인 약 데이터 추가*/
	int joinUser(ArrayList<UserDiseaseDTO> userDiseaseList, ArrayList<UserDrugDTO> userDrugList);
	
}
