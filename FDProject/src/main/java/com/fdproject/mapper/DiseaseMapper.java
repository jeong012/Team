package com.fdproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fdproject.domain.DiseaseDTO;

@Mapper
public interface DiseaseMapper {
	public List<DiseaseDTO> selectDiseaseListFive();	
}
