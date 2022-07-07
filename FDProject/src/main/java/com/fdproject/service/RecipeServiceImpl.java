package com.fdproject.service;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	public List<RecipeDTO> getRecipeList(RecipeDTO params) { // list
		List<RecipeDTO> recipeList = Collections.emptyList();

		int recipeTotalCount = recipeMapper.selectRecipeTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(recipeTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (recipeTotalCount > 0 && params.getWriter() == null) {
			recipeList = recipeMapper.selectRecipeList(params);
			System.out.println("나여기 1");
		}
		else if(recipeTotalCount > 0 && params.getWriter() != null) {
			recipeList = recipeMapper.selectWriterRecipeList(params);
			System.out.println("나여기 2");
		}
		return recipeList;
	}

	@Override
	public List<RecipeDTO> getMyRecipeList(RecipeDTO params) { // mylist
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
		// recommended Number plus
		recipeMapper.updateRecommendedNumber(cartDTO);
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
		// recommended Number minus
		recipeMapper.minusRecommendedNumber(cartDTO);
		int count = recipeMapper.deleteCart(cartDTO);
		return (count == 1) ? true : false;
	}

	@Override
	public boolean uploadRecipe(MultipartFile file, Map<String, Object> data) {
		// 파일 처리
		String uploadFolder = "C:\\Users\\i\\Documents\\workspace-spring-tool-suite-4-4.14.1.RELEASE\\Team\\Team\\FDProject\\src\\main\\resources\\static\\assets\\img\\recipeImages";

		System.out.println("Upload File Name:" + file.getOriginalFilename());
		System.out.println("Upload File Size:" + file.getSize());

		String uploadFileName = file.getOriginalFilename();
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

		System.out.println("only file name:" + uploadFileName);

		File saveFile = new File(uploadFolder, uploadFileName);
		try {
			file.transferTo(saveFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("에러 발생");
		}
		//data 처리		
		RecipeDTO params = new RecipeDTO();
		params.setImgFile(uploadFileName);		
		params.setTip((String)data.get("tip"));
		params.setStorage((String)data.get("storage"));
		params.setTitle((String)data.get("title"));
		params.setStep((String)data.get("step"));
		params.setFoodIngredients((String)data.get("foodIngredients")); 
		// writer 지정
		params.setWriter("test");
		// recipe_no 지정
		int recipe_no = recipeMapper.getRecipeNo();
		recipe_no += 1;
		params.setRecipeNo(recipe_no);
		// upload 동작 수행.
		int count = recipeMapper.uploadRecipe(params);

		System.out.println("params:" + params);
		return (count == 1) ? true : false;
	}

	

}
