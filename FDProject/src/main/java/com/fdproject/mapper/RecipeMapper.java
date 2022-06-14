package com.fdproject.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fdproject.domain.RecipeDTO;

@Mapper
public interface RecipeMapper {
	public List<RecipeDTO> selectRecipeList();
	public List<RecipeDTO> selectRecipeListByDiseaseField(String disease_Field);
}
