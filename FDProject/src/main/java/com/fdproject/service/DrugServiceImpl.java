package com.fdproject.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.domain.UserDrugDTO;
import com.fdproject.mapper.DrugMapper;
import com.fdproject.paging.PaginationInfo;
import com.fdproject.util.GrammerUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DrugServiceImpl implements DrugService {

    private final DrugMapper drugMapper;
    @Override
    public List<DrugDTO> getDrugList(String id, DrugDTO params, String takeYn) {
        List<DrugDTO> drugList = Collections.emptyList();

        int drugTotalCount = drugMapper.selectDrugTotalCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(drugTotalCount);

        params.setPaginationInfo(paginationInfo);

        if (GrammerUtils.isStringEmpty(id) == false) {
            UserDrugDTO userDrug = new UserDrugDTO();
            userDrug.setUserId(id);
                List<String> value = drugMapper.selectKeywords(userDrug);
                if(value.isEmpty() == true && takeYn != null){
                    return drugList;
                }
                System.out.println(value);

            String keywords = GrammerUtils.str(value);
            String[] strArr = keywords.split(",");
            HashSet<String> arr = new HashSet<String>(Arrays.asList(strArr));
            List<String> result = new ArrayList<String>(arr);
            
            String str = GrammerUtils.str(result);
            
            List<String> diseaseList = drugMapper.selectDiseases(id);
            String diseases = GrammerUtils.str(diseaseList);
            String[] diseaseArr = diseases.split(",");
            HashSet<String> hashset = new HashSet<String>(Arrays.asList(diseaseArr));
            List<String> resultList = new ArrayList<String>(hashset);
            
            String userDiseases = GrammerUtils.str(resultList);
            str = str + "|" + userDiseases;
            str = str.replaceAll(",", "|");
            
            params.setParams(str);
            if (GrammerUtils.isStringEmpty(takeYn) == false && takeYn.equals("Y")) {
                params.setTakeYn(takeYn);
            } else if (GrammerUtils.isStringEmpty(takeYn) == false && takeYn.equals("N")) {
                params.setTakeYn(takeYn);
            } else {
                System.out.println("errorMessage!");
            }
            drugTotalCount = drugMapper.selectDrugTotalCount(params);

                paginationInfo.setTotalRecordCount(drugTotalCount);

                params.setPaginationInfo(paginationInfo);
                drugList = drugMapper.drugList(params);
                return drugList;
            } //
            params.setTakeYn("");
            drugList = drugMapper.drugList(params);

            return drugList;

    }
    
    //상비약
    public List<DrugDTO> getHouseDrugList(DrugDTO params){
    	List<DrugDTO> housedrugList = Collections.emptyList();
    	
    	int drugTotalCount = drugMapper.selectDrugTotalCount(params);
    	
    	if(drugTotalCount>0) {
    		housedrugList = drugMapper.housedrugList(params);
    	}
    	
    	return housedrugList;
}

    public DrugDTO getDrug(int drugNo) {
        DrugDTO drug = drugMapper.getDrugDetail(drugNo);
        return drug;
    }

    @Override
    public boolean addMyDrug(String id, DrugsCartDTO cartDTO) {
        cartDTO.setUserId(id);
        int count = 0;
        List<DrugsCartDTO> list = drugMapper.myDrugList(cartDTO.getUserId());
        if (!list.isEmpty()) {
            for (DrugsCartDTO cart : list) {
                if (cart.getDrugNo() != cartDTO.getDrugNo()) {
                    count = drugMapper.addCart(cartDTO);
                    return (count == 1) ? true : false;
                }
                count = 0;
            }
        }
        count = drugMapper.addCart(cartDTO);
        return (count == 1) ? true : false;
    }
    
    
    //마이페이지 등록한 약 리스트
	public List<DrugDTO> getUserDrugList(String id, DrugDTO params) {
        List<DrugDTO> userdrugList = Collections.emptyList();
        
        if (GrammerUtils.isStringEmpty(id) == false && !id.equals("null")) {
            UserDrugDTO userDrug = new UserDrugDTO();
            
            userDrug.setUserId(id);
            List<UserDrugDTO> userdrugcart = drugMapper.getUserDrug(userDrug);
            
            if(userdrugcart.size()<1) { //저장한 질병이 없을 떄
     
                return userdrugList;
            } else {
            	String str = "";
	            for(int i = 0; i < userdrugcart.size();i++ ) {
	            	str += userdrugcart.get(i);
	            	str += ",";
	            }
	            str = str.substring(0,str.length() - 1);
	            str = str.replaceAll("\"", "");
	            params.setCart(str);
	            
	            userdrugList = drugMapper.userdrugList(params);
	            return userdrugList;
            }
           
        }

        return userdrugList;
	}

    @Override
    public boolean deleteMyDrug(DrugsCartDTO cartDTO) {
        int count = drugMapper.deleteCart(cartDTO);
        return (count == 1) ? true : false;
    }

    @Override
    public DrugsCartDTO getMyDrug(int drugNo) {

        return drugMapper.getMyDrug(drugNo);
    }

    @Override
    public List<DrugDTO> getMyDrugList(String id, DrugDTO params) {
        List<DrugDTO> drugList = Collections.emptyList();

        DrugsCartDTO cartDTO = new DrugsCartDTO();
        cartDTO.setUserId(id);
        params.setCartDTO(cartDTO); //약객체.(재원이가 만든 관심약품DTO)(카트객체 - 유저ID 지정된.)

        int drugTotalCount = drugMapper.selectMyDrugTotalCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(drugTotalCount); //전체 드러그 수를 pagination에 넘겨서 저장시키게끔

        params.setPaginationInfo(paginationInfo);
        
        if(drugTotalCount >0) {
        	drugList = drugMapper.getMyDrugList(params);
        }
        
        return drugList;
    }

    @Override
    public JsonArray getSearchKeyword(String searchValue) throws IOException {
        JsonArray arrayObj = new JsonArray();
        JsonObject jsonObj = null;
        List<String> list1 = drugMapper.getSearchKeyword(searchValue);
        List<String> list2 = drugMapper.getNameKeyword(searchValue);
        List<String> resultList = new ArrayList<>();
        resultList.addAll(list1);
        resultList.addAll(list2);
        for (String str : resultList) {
            jsonObj = new JsonObject();
            jsonObj.addProperty("data", str);
            arrayObj.add(jsonObj);
        }
        return arrayObj;
    }

    /** 회원가입 - 약 리스트 조회 사용*/
    @Override
    public List<DrugDTO> getJoinDrugList() {
        List<DrugDTO> drugList = drugMapper.getJoinDrugList();
        return drugList;
    }
    
	/** 마이페이지 - 약 리스트 조회 (사용자가 복용중인 약 제외) */
    @Override
    public List<DrugDTO> getMyPageDrugList(String userId){
    	List<DrugDTO> drugList = drugMapper.getMyPageDrugList(userId);
        return drugList;
    }

    /** 마이페이지 - 사용자가 복용중인 약 조회 */
    @Override
	public List<DrugDTO> getUserDrugList(String userId){
    	List<DrugDTO> drugList = drugMapper.getUserDrugList(userId);
        return drugList;
	}

	/** 회원 관리 - 사용자 복용중인 약 조회 */
	@Override
	public String getUserDrugByAdmin(int userNo) {
		String userDrugs = "";

		List<Map<String,Object>> userDrugMap = drugMapper.getUserDrugByAdmin(userNo);
		for(Map<String,Object> map: userDrugMap) {
			userDrugs += map.get("NAME").toString() + "\n";
		}
		
		if(userDrugs != "") {
			userDrugs = userDrugs.substring(0, userDrugs.length()-1);
		}
		
		return userDrugs;
	}

    @Override
    public List<String> getMyDrug(String id) {
        UserDrugDTO userDrug = new UserDrugDTO();
        userDrug.setUserId(id);
        return drugMapper.selectKeywords(userDrug);
    }
}
