package com.fdproject.service;
import java.util.List;
import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.UserDiseaseDTO;

public interface DiseaseService {

	List<DiseaseDTO> getDiseaseList(String id, DiseaseDTO params);
	List<DiseaseDTO> getDiseaseListFive();	
	
    /** 회원가입 - 질병 리스트 조회 사용*/
	List<DiseaseDTO> getJoinDiseaseList();
}

