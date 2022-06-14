package com.fdproject.service;

import java.util.List;

import com.fdproject.domain.RecipeDTO;

public interface RecipeService {
	public List<RecipeDTO> getRecipeList();
	
	public List<RecipeDTO> getRecipeListByDiseaseField(String disease_Field);
}
