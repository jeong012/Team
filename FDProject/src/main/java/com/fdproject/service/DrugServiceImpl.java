package com.fdproject.service;

import java.util.*;
import java.util.stream.Collectors;

import com.fdproject.domain.DrugsCartDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.UserDrugDTO;
import com.fdproject.mapper.DrugMapper;
import com.fdproject.paging.PaginationInfo;
import com.fdproject.util.GrammerUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrugServiceImpl implements DrugService {

    private final DrugMapper drugMapper;

    @Transactional(readOnly = true)
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
            System.out.println(value);
            String keywords = GrammerUtils.str(value);
            String[] strArr = keywords.split(",");
            HashSet<String> arr = new HashSet<String>(Arrays.asList(strArr));
            List<String> result = new ArrayList<String>(arr);
            String str = GrammerUtils.str(result);
            System.out.println(str);
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
        }
        params.setTakeYn("");
        drugList = drugMapper.drugList(params);

        return drugList;
    }
    
    public List<DrugDTO> getHouseDrugList(DrugDTO params){
    	List<DrugDTO> housedrugList = Collections.emptyList();
    	
    	int drugTotalCount = drugMapper.selectDrugTotalCount(params);
    	
    	if(drugTotalCount>0) {
    		housedrugList = drugMapper.housedrugList(params);
    	}
    	
    	return housedrugList;
    }

    @Transactional(readOnly = true)
    public DrugDTO getDrug(int drugNo) {
        DrugDTO drug = drugMapper.getDrugDetail(drugNo);
        return  drug;
    }

    public String addDrugCart(DrugsCartDTO cartDTO) {
        cartDTO.setUserId("test");
        int count = drugMapper.addCart(cartDTO);
        return (count == 1) ? "true" : "false" ;
    }
}
