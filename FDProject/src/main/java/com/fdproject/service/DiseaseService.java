package com.fdproject.service;
import java.util.List;
import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.UserDiseaseDTO;

public interface DiseaseService {

	List<DiseaseDTO> getDiseaseList(String id, DiseaseDTO params);
	List<DiseaseDTO> getDiseaseListFive();	
}

