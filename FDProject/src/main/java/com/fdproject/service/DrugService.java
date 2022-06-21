package com.fdproject.service;

import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DrugService {


    List<DrugDTO> getDrugList(String id, DrugDTO params, String takeYn);
    DrugDTO getDrug(int drugNo);
    List<DrugDTO> getHouseDrugList(DrugDTO params);

    String addDrugCart(DrugsCartDTO cartDTO);

}
