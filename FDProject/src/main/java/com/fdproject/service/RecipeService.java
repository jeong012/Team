package com.fdproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.RecipesCartDTO;

public interface RecipeService {
	
	public List<RecipeDTO> getRecipeList(RecipeDTO params); //recipe list
	
	public List<RecipeDTO> getMyRecipeList(RecipeDTO params); // my recipe list		
	
	public RecipeDTO getRecipeInfo(String Recipe_info);
	
	public Integer uphit(String Recipe_No);
	
	public boolean addMyRecipe(RecipesCartDTO cartDTO);
	
	public boolean deleteMyRecipe(RecipesCartDTO cartDTO);
	
	public boolean uploadRecipe(MultipartFile file, Map<String, Object> data) throws Exception;
	
	public String randomFileName(String uploadFileName,byte[] file) throws Exception;
		
}
