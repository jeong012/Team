package com.fdproject.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;
import com.fdproject.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    
	@Override
    @Transactional
	public int joinUser(ArrayList<UserDiseaseDTO> userDiseaseList, ArrayList<UserDrugDTO> userDrugList) {
    	int isInserted = 0;

    	/** 회원가입 - 사용자 지병 데이터 추가*/
    	for (UserDiseaseDTO userDiseaseDTO : userDiseaseList) {
        	userDiseaseDTO.setUserId("[Manager]");
        	
            isInserted = userMapper.insertUserDisease(userDiseaseDTO);
    	}

        /** 회원가입 - 사용자 복용중인 약 데이터 추가*/
    	for (UserDrugDTO userDrugDTO : userDrugList) {
			userDrugDTO.setUserId("[Manager]");
			
	        isInserted = userMapper.insertUserDrug(userDrugDTO);
    	}
    	
		return isInserted;
	}

}
