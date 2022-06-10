package com.fdproject.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fdproject.domain.ReceipeDTO;

@Mapper
public interface ReceipeMapper {
	public List<ReceipeDTO> selectReceipeList();
	public List<ReceipeDTO> selectReceipeListByDiseaseField(String disease_Field);
}
