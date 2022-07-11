package com.fdproject.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.RecipesCartDTO;
import com.fdproject.domain.UserDiseaseDTO;

public interface RecipeService {
	
	public List<RecipeDTO> getRecipeList(RecipeDTO params); //recipe list
	
	public List<RecipeDTO> getMyRecipeList(RecipeDTO params, Principal principal); // my recipe list		
	
	public RecipeDTO getRecipeInfo(String Recipe_info, Principal principal);
	
	public Integer uphit(String Recipe_No);
	
	public boolean addMyRecipe(RecipesCartDTO cartDTO, Principal principal);
	
	public boolean deleteMyRecipe(RecipesCartDTO cartDTO, Principal principal);
	
	public boolean uploadRecipe(MultipartFile file, Map<String, Object> data, Principal principal) throws Exception;
	
	public String randomFileName(String uploadFileName,byte[] file) throws Exception;
	
		
}
