package com.fdproject.service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.mapper.DiseaseMapper;
import com.fdproject.util.GrammerUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {
	
	private final DiseaseMapper diseaseMapper;

	@Override
	public List<DiseaseDTO> getDiseaseList() {		
		return diseaseMapper.selectDiseaseList();
	}

	@Override
	public List<DiseaseDTO> getDiseaseList(String id, DiseaseDTO params) {
        List<DiseaseDTO> diseaseList = Collections.emptyList();
        
        if (GrammerUtils.isStringEmpty(id) == false && !id.equals("null")) {
            UserDiseaseDTO userDisease = new UserDiseaseDTO();
            
            userDisease.setUserId(id);
            List<UserDiseaseDTO> userdiseasecart = diseaseMapper.getUserDisease(userDisease);
            
            if(userdiseasecart.size()<1) { //저장한 질병이 없을 떄
     
                return diseaseList;
            } else {
            	String str = "";
	            for(int i = 0; i < userdiseasecart.size();i++ ) {
	            	str += userdiseasecart.get(i);
	            	str += ",";
	            }
	            str = str.substring(0,str.length() - 1);
	            str = str.replaceAll("\"", "");
	            params.setCart(str);
	            diseaseList = diseaseMapper.diseaseList(params);
	            return diseaseList;
            }
            
        }

        return diseaseList;
	}

    /** 회원가입 - 질병 리스트 조회 사용*/
	@Override
	public List<DiseaseDTO> getJoinDiseaseList() {
        List<DiseaseDTO> diseaseList = diseaseMapper.getJoinDiseaseList();
		return diseaseList;
	}

	@Override
	public List<DiseaseDTO> getUserDiseaseList(Principal principal) {		
		return diseaseMapper.selectUserDiseaseList(principal.getName());
	}

	/** 마이페이지 - 질병 리스트 조회 (사용자가 앓고 있는 지병 제외) */
	@Override
	public List<DiseaseDTO> getMyPageDiseaseList(String userId){
        List<DiseaseDTO> diseaseList = diseaseMapper.getMyPageDiseaseList(userId);
		return diseaseList;
	}
	
	/** 마이페이지 - 사용자가 앓고 있는 지병 조회 */
	@Override
	public List<DiseaseDTO> getUserDiseaseList(String userId){
		 List<DiseaseDTO> userDiseaseList = diseaseMapper.getUserDiseaseList(userId);
			return userDiseaseList;
	}

	/** 사용자 앓고있는 지병 조회*/
	@Override
	public String getUserDiseaseByAdmin(int userNo) {
		String userDiseases = "";
		
		List<Map<String,Object>> userDiseaseMap = diseaseMapper.getUserDiseaseByAdmin(userNo);
		for(Map<String,Object> map: userDiseaseMap) {
			userDiseases += map.get("NAME").toString() + "\n";
		}
		
		if(userDiseases != "") {
			userDiseases = userDiseases.substring(0, userDiseases.length()-1);
		}
		
		return userDiseases;
	}

}
