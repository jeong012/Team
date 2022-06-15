package com.fdproject.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

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

    public List<DrugDTO> getDrugList(String id, DrugDTO params, String takeYn) {
        List<DrugDTO> drugList = Collections.emptyList();

        int drugTotalCount = drugMapper.selectDrugTotalCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(drugTotalCount);

        params.setPaginationInfo(paginationInfo);

        if (GrammerUtils.isStringEmpty(id) == false) {
            UserDrugDTO userDrug = new UserDrugDTO();
            userDrug.setUserId(id);
            List<UserDrugDTO> userDrugList = drugMapper.getUserDrug(userDrug);
            List<String> value = drugMapper.selectKeywords(userDrugList);
            String keywords = StringUtils.join(value, ",");
            String[] strArr = keywords.split(",");
            String result = StringUtils.join(strArr, "|");
            params.setParams(result);
            System.out.println(takeYn);
            if (GrammerUtils.isStringEmpty(takeYn) == false && takeYn.equals("Y")) {
                System.out.println(takeYn);
                params.setTakeYn(takeYn);
            }
            drugList = drugMapper.drugList(params);


            return drugList;
        } else {
            drugList = drugMapper.drugList(params);
        }
        return drugList;
    }
}
