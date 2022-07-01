package com.fdproject.mappers;

import com.fdproject.domain.DrugDTO;
import com.fdproject.mapper.DrugMapper;
import com.fdproject.paging.Criteria;
import com.fdproject.paging.PaginationInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@SpringBootTest
public class DrugMapperTest {

    @Autowired
    DrugMapper drugMapper;

    @Test
    public void getDrugList() {
        Criteria criteria = new Criteria();
        criteria.setCurrentPageNo(1);
        criteria.setRecordsPerPage(10);
        criteria.setPageSize(10);

        PaginationInfo paginationInfo = new PaginationInfo(criteria);

        DrugDTO drugDTO = new DrugDTO();
        drugDTO.setPaginationInfo(paginationInfo);

        int drugTotalCount = drugMapper.selectDrugTotalCount(drugDTO);

        paginationInfo.setTotalRecordCount(drugTotalCount);
        drugDTO.setPaginationInfo(paginationInfo);
        drugDTO.setParams("");

        List<DrugDTO> drugList = drugMapper.drugList(drugDTO);

    }

//    @Test
//    public void getRiskDrugList() {
//
//        List<String> keyword = drugMapper.selectKeyword();
//        String keywords = StringUtils.join(keyword, ",");
//
//        String[] strArr = keywords.split(",");
//        System.out.println(strArr.toString());
//
//        Criteria criteria = new Criteria();
//        criteria.setCurrentPageNo(1);
//        criteria.setRecordsPerPage(10);
//        criteria.setPageSize(10);
//
//        PaginationInfo paginationInfo = new PaginationInfo(criteria);
//
//        DrugDTO drugDTO = new DrugDTO();
//        drugDTO.setPaginationInfo(paginationInfo);
//
//        int drugTotalCount = drugMapper.selectDrugTotalCount(drugDTO);
//
//        paginationInfo.setTotalRecordCount(drugTotalCount);
//        drugDTO.setPaginationInfo(paginationInfo);
//
//        String params = StringUtils.join(strArr, "|");
//
//        drugDTO.setParams(params);
//
//        List<DrugDTO> drugList = drugMapper.drugList(drugDTO);
//    }
}
