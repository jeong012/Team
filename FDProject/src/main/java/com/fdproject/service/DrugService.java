package com.fdproject.service;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import com.google.gson.JsonArray;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface DrugService {


    List<DrugDTO> getDrugList(String id, DrugDTO params, String takeYn);
    
    DrugDTO getDrug(int drugNo);

    
    /*마이페이지 약 리스트*/
	List<DrugDTO> getUserDrugList(String id, DrugDTO params);

    List<DrugDTO> getHouseDrugList(DrugDTO params);

    boolean addMyDrug(String id, DrugsCartDTO cartDTO);

    boolean deleteMyDrug(DrugsCartDTO cartDTO);

    DrugsCartDTO getMyDrug(int drugNo);

    List<DrugDTO> getMyDrugList(String id, DrugDTO drugDTO);

    JsonArray getSearchKeyword(String searchValue) throws IOException;

    /** 회원가입 - 약 리스트 조회 사용*/
    List<DrugDTO> getJoinDrugList();
}
