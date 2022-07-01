package com.fdproject.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.RecipesCartDTO;
import com.fdproject.mapper.RecipeMapper;
import com.fdproject.paging.PaginationInfo;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeMapper recipeMapper;

	@Override
	public List<RecipeDTO> getRecipeList(RecipeDTO params) { //list
		List<RecipeDTO> recipeList = Collections.emptyList();

		int recipeTotalCount = recipeMapper.selectRecipeTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(recipeTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (recipeTotalCount > 0) {
			recipeList = recipeMapper.selectRecipeList(params);
		}

		return recipeList;
	}

	@Override
	public List<RecipeDTO> getMyRecipeList(RecipeDTO params) { //mylist
		List<RecipeDTO> recipeList = Collections.emptyList();

		RecipesCartDTO cartDTO = new RecipesCartDTO();
		cartDTO.setUserId("test");
		params.setCartDTO(cartDTO); // 레시피객체.(재원이가 만든 관심약품DTO)(카트객체 - 유저ID 지정된.)

		int recipeTotalCount = recipeMapper.selectRecipeTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(recipeTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (recipeTotalCount > 0) {
			recipeList = recipeMapper.getMyRecipeList(params);
		}

		return recipeList;
	}

	@Override
	public RecipeDTO getRecipeInfo(String Recipe_no) {
		RecipeDTO recipeDTO = recipeMapper.selectRecipeInfo(Recipe_no);
		recipeDTO.setCartDTO(recipeMapper.selectMyRecipe(Recipe_no));
		return recipeDTO;
	}

	@Override
	public Integer uphit(String Recipe_No) {
		return recipeMapper.UpdateUphit(Recipe_No);
	}

	@Override
	public boolean addMyRecipe(RecipesCartDTO cartDTO) {
		cartDTO.setUserId("test");
		int count = 0;
		List<RecipesCartDTO> list = recipeMapper.myRecipeList(cartDTO.getUserId());
		if (!list.isEmpty()) {
			for (RecipesCartDTO cart : list) {
				if (cart.getRecipeNo() != cartDTO.getRecipeNo()) {
					count = recipeMapper.addCart(cartDTO);
					return (count == 1) ? true : false;
				}
				count = 0;
			}
		}
		count = recipeMapper.addCart(cartDTO);
		return (count == 1) ? true : false;
	}

	@Override
	public boolean deleteMyRecipe(RecipesCartDTO cartDTO) {
		int count = recipeMapper.deleteCart(cartDTO);
		return (count == 1) ? true : false;
	}

}
