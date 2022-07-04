package com.fdproject.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.RecipesCartDTO;

@Mapper
public interface RecipeMapper {
	List<RecipeDTO> selectRecipeList(RecipeDTO params);	
	RecipeDTO selectRecipeInfo(String Recipe_no);
    Integer UpdateUphit(String Recipe_no);
	int selectRecipeTotalCount(RecipeDTO params);
	RecipesCartDTO selectMyRecipe(String Recipe_no);
	int addCart(RecipesCartDTO cartDTO);
	int deleteCart(RecipesCartDTO cartDTO);
	 List<RecipesCartDTO> myRecipeList(String id);
	 List<RecipeDTO> getMyRecipeList(RecipeDTO params);
	 int selectMyRecipeTotalCount(RecipeDTO params);
	 int getRecommendedNumber(RecipeDTO params);
	 int uploadRecipe(RecipeDTO params);
	 int getRecipeNo();
	 
	 
}
