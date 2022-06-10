package com.fdproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.mapper.DiseaseMapper;


@Service
public class DiseaseServiceImpl implements DiseaseService {
	
	@Autowired
	private DiseaseMapper diseaseMapper;
	
	@Override
	public List<DiseaseDTO> getDiseaseListFive() {		
		return diseaseMapper.selectDiseaseListFive();
	}

}
