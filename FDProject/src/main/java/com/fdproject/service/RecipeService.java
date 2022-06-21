package com.fdproject.service;

import java.util.List;

import com.fdproject.domain.RecipeDTO;

public interface RecipeService {
	public List<RecipeDTO> getRecipeList(RecipeDTO params);		
	
	public RecipeDTO getRecipeInfo(String Recipe_info);
	
	public Integer uphit(String Recipe_No);
}
