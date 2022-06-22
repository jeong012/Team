package com.fdproject.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.RecipeDTO;

@Mapper
public interface RecipeMapper {
	public List<RecipeDTO> selectRecipeList(RecipeDTO recipeDTO);	
	public RecipeDTO selectRecipeInfo(String Recipe_no);
	public Integer UpdateUphit(String Recipe_no);
	int selectRecipeTotalCount(RecipeDTO recipeDTO);
	
}
