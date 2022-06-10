package com.fdproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdproject.domain.ReceipeDTO;
import com.fdproject.mapper.ReceipeMapper;

@Service
public class ReceipeServiceImpl implements ReceipeService {

	@Autowired
	private ReceipeMapper receipeMapper;
	
	@Override
	public List<ReceipeDTO> getReceipeList(){
		return receipeMapper.selectReceipeList();
	}

	@Override
	public List<ReceipeDTO> getReceipeListByDiseaseField(String disease_Field) {		
		return receipeMapper.selectReceipeListByDiseaseField(disease_Field);
	};
	
	
}
