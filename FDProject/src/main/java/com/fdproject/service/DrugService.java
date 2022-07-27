package com.fdproject.service;

import java.io.IOException;
import java.util.List;

import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.domain.UserDrugDTO;
import com.google.gson.JsonArray;

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
    
	/** 마이페이지 - 약 리스트 조회 (사용자가 복용중인 약 제외) */
    List<DrugDTO> getMyPageDrugList(String userId);

    /** 마이페이지 - 사용자가 복용중인 약 조회 */
	List<DrugDTO> getUserDrugList(String userId);
	
	/** 회원 관리 - 사용자 복용중인 약 조회 */
	String getUserDrugByAdmin(int userNo);

    List<String> getMyDrug(String id);
	
}
