package com.fdproject.service;

import java.util.List;

import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.RecipesCartDTO;

public interface RecipeService {
	public List<RecipeDTO> getRecipeList(RecipeDTO params);		
	
	public RecipeDTO getRecipeInfo(String Recipe_info);
	
	public Integer uphit(String Recipe_No);
	
	public boolean addMyRecipe(RecipesCartDTO cartDTO);
	
	public boolean deleteMyRecipe(RecipesCartDTO cartDTO);
	
	
}
