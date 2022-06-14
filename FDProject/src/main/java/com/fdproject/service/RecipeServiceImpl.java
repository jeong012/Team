package com.fdproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdproject.domain.RecipeDTO;
import com.fdproject.mapper.RecipeMapper;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeMapper recipeMapper;
	
	@Override
	public List<RecipeDTO> getRecipeList(){
		return recipeMapper.selectRecipeList();
	} 

	@Override
	public List<RecipeDTO> getRecipeListByDiseaseField(String disease_Field) {		
		return recipeMapper.selectRecipeListByDiseaseField(disease_Field);
	}

	@Override
	public RecipeDTO getRecipeInfo(String Recipe_no) {		
		return recipeMapper.selectRecipeInfo(Recipe_no);
	}

	@Override
	public Integer uphit(String Recipe_No) {		
		return recipeMapper.UpdateUphit(Recipe_No);
	};
	
	
}
