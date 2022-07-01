package com.fdproject.service;

import java.util.*;

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
    @Override
    public List<DrugDTO> getHouseDrugList(DrugDTO params) {
        List<DrugDTO> housedrugList = Collections.emptyList();

        int drugTotalCount = drugMapper.selectDrugTotalCount(params);

        if (drugTotalCount > 0) {
            housedrugList = drugMapper.housedrugList(params);
        }

        return housedrugList;
    }

    public DrugDTO getDrug(int drugNo) {
        DrugDTO drug = drugMapper.getDrugDetail(drugNo);
        return drug;
    }

    @Override
    public boolean addMyDrug(DrugsCartDTO cartDTO) {
        cartDTO.setUserId("test");
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
    public List<DrugDTO> getMyDrugList(DrugDTO params) {
        List<DrugDTO> drugList = Collections.emptyList();

        DrugsCartDTO cartDTO = new DrugsCartDTO();
        cartDTO.setUserId("test");
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
    public List<String> getSearchKeyword() {
        return drugMapper.getSearchKeyword();
    }

    /** 회원가입 - 약 리스트 조회 사용*/
    @Override
    public List<DrugDTO> getJoinDrugList() {

        List<DrugDTO> drugList = new ArrayList<>();
        drugList = drugMapper.joinDrugList();

        return drugList;
    }
}
