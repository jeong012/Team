package com.fdproject.mapper;

import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.UserDrugDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DrugMapper {

    List<DrugDTO> drugList(DrugDTO drugDTO);

    List<DrugDTO> riskDrugList(DrugDTO drugDTO);

    List<String> selectKeyword();

    int selectDrugTotalCount(DrugDTO drugDTO);

}
