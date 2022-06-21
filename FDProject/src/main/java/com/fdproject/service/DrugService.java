package com.fdproject.service;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DrugService {


    List<DrugDTO> getDrugList(String id, DrugDTO params, String takeYn);
    
    DrugDTO getDrug(int drugNo);
    
    String addDrugCart(DrugsCartDTO cartDTO);
    
    /*상비약 리스트*/
    List<DrugDTO> getHouseDrugList(DrugDTO params);
    
    /*마이페이지 약 리스트*/
	List<DrugDTO> getUserDrugList(String id, DrugDTO params);

}
