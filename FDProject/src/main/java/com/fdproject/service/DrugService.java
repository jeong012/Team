package com.fdproject.service;

import com.fdproject.domain.DrugDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DrugService {

    List<DrugDTO> getDrugList(String id, DrugDTO params);
    
    List<DrugDTO> getHouseDrugList(DrugDTO params);
}
