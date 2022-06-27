package com.fdproject.mapper;

import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.domain.UserDrugDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DrugMapper {

    List<DrugDTO> drugList(DrugDTO drugDTO);

    List<String> selectKeywords(UserDrugDTO userDrugDTO);

    int selectDrugTotalCount(DrugDTO drugDTO);
    
    List<DrugDTO> housedrugList(DrugDTO drugDTO);

    DrugDTO getDrugDetail(int drugNo);

    List<DrugDTO> getMyDrugList(DrugDTO drugDTO);

    int addCart(DrugsCartDTO cartDTO);

    int deleteCart(DrugsCartDTO cartDTO);

    DrugsCartDTO getMyDrug(int drugNo);

    List<DrugsCartDTO> myDrugList(String id);

    int selectMyDrugTotalCount(DrugDTO drugDTO);

    List<String> getSearchKeyword();

    /** 회원가입 - 약 리스트 조회 사용*/
    List<DrugDTO> joinDrugList();

}
