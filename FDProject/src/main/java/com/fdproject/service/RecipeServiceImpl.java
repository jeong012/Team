package com.fdproject.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdproject.domain.RecipeDTO;


import com.fdproject.mapper.RecipeMapper;
import com.fdproject.paging.PaginationInfo;


@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeMapper recipeMapper;
	
	@Override
	 public List<RecipeDTO> getRecipeList(RecipeDTO params) {
        List<RecipeDTO> recipeList = Collections.emptyList();

        int recipeTotalCount = recipeMapper.selectRecipeTotalCount(params);
        
        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(recipeTotalCount);

        params.setPaginationInfo(paginationInfo);
        
        if(recipeTotalCount > 0) {
        	recipeList = recipeMapper.selectRecipeList(params);
        }
      
       
        return recipeList;
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
