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

    DrugDTO getDrugDetail(int drugNo);

    List<DrugDTO> getMyDrugList(DrugDTO drugDTO);

    int addCart(DrugsCartDTO cartDTO);

    int deleteCart(DrugsCartDTO cartDTO);

    DrugsCartDTO getMyDrug(int drugNo);

    List<DrugsCartDTO> myDrugList(String id);

}
