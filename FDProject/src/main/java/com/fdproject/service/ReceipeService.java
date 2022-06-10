package com.fdproject.service;

import java.util.List;

import com.fdproject.domain.ReceipeDTO;

public interface ReceipeService {
	public List<ReceipeDTO> getReceipeList();
	
	public List<ReceipeDTO> getReceipeListByDiseaseField(String disease_Field);
}
