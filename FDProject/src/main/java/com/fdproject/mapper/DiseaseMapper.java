package com.fdproject.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.UserDiseaseDTO;
	
@Mapper
public interface DiseaseMapper {

	List<UserDiseaseDTO> getUserDisease(UserDiseaseDTO userDisease);

	List<DiseaseDTO> diseaseList(DiseaseDTO params);
	
    List<DiseaseDTO> selectDiseaseListFive();


}
