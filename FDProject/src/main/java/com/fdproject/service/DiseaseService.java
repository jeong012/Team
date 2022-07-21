package com.fdproject.service;
import java.security.Principal;
import java.util.List;

import com.fdproject.domain.DiseaseDTO;

public interface DiseaseService {

	List<DiseaseDTO> getDiseaseList(String id, DiseaseDTO params);
	List<DiseaseDTO> getDiseaseList();	
	
    /** 회원가입 - 질병 리스트 조회 사용*/
	List<DiseaseDTO> getJoinDiseaseList();
	
	public List<DiseaseDTO> getUserDiseaseList(Principal principal);

	/** 마이페이지 - 질병 리스트 조회 (사용자가 앓고 있는 지병 제외) */
	List<DiseaseDTO> getMyPageDiseaseList(String userId);
	
	/** 마이페이지 - 사용자가 앓고 있는 지병 조회 */
	List<DiseaseDTO> getUserDiseaseList(String userId);
	
	/** 회원 관리 - 사용자 앓고있는 지병 조회 */
	String getUserDiseaseByAdmin(int userNo);
}

