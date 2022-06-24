package com.fdproject.service;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DrugService {


    List<DrugDTO> getDrugList(String id, DrugDTO params, String takeYn);
    
    DrugDTO getDrug(int drugNo);

    
    /*마이페이지 약 리스트*/
	List<DrugDTO> getUserDrugList(String id, DrugDTO params);

    List<DrugDTO> getHouseDrugList(DrugDTO params);

    boolean addMyDrug(DrugsCartDTO cartDTO);


    boolean deleteMyDrug(DrugsCartDTO cartDTO);

    DrugsCartDTO getMyDrug(int drugNo);
    
    /** 회원가입 - 약 리스트 조회 사용*/
    List<DrugDTO> getJoinDrugList();
}
