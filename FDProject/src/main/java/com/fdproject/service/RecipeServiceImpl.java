package com.fdproject.service;

import java.io.File;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

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
		int recipeTotalCount = 0;
		
		if (params.getWriter() == null) {
			recipeTotalCount = recipeMapper.selectRecipeTotalCount(params);			
		} else if ((params.getWriter()).equals("writer")) { // 유저 작성 레시피
			recipeTotalCount = recipeMapper.selectWriterRecipeTotalCount(params);			
		} else { // 본인 작성 레시피
			recipeTotalCount = recipeMapper.selectMyWriterRecipeTotalCount(params);			
		}
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(recipeTotalCount);

		params.setPaginationInfo(paginationInfo);
		
		if (recipeTotalCount > 0) {			
			if (params.getWriter() == null) {
				recipeList = recipeMapper.selectRecipeList(params);				
			} else if ((params.getWriter()).equals("writer")) { // 유저 작성 레시피
				recipeList = recipeMapper.selectWriterRecipeList(params);				
			} else { // 본인 작성 레시피
				recipeList = recipeMapper.selectMyWriteRecipeList(params);				
			}
		}
		System.out.println("recipeList:" + recipeList);
		return recipeList;
	}

	@Override
	public List<RecipeDTO> getMyRecipeList(RecipeDTO params, Principal principal) { // mylist
		List<RecipeDTO> recipeList = Collections.emptyList();

		RecipesCartDTO cartDTO = new RecipesCartDTO();

		cartDTO.setUserId(principal.getName());

		params.setCartDTO(cartDTO); // 레시피객체.(재원이가 만든 관심약품DTO)(카트객체 - 유저ID 지정된.)		
		int recipeTotalCount = recipeMapper.selectMyRecipeTotalCount(params);		
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(recipeTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (recipeTotalCount > 0) {
			recipeList = recipeMapper.getMyRecipeList(params); // 완료
		}

		return recipeList;
	}

	@Override
	public RecipeDTO getRecipeInfo(String Recipe_no, Principal principal) {
		RecipeDTO recipeDTO = recipeMapper.selectRecipeInfo(Recipe_no);
		// 로그인 세션이 존재하면
		if (principal != null) {
			RecipesCartDTO cartDTO = new RecipesCartDTO();

			cartDTO.setRecipeNo(Integer.parseInt(Recipe_no));
			cartDTO.setUserId(principal.getName());

			recipeDTO.setCartDTO(recipeMapper.selectMyRecipe(cartDTO));
		}
		return recipeDTO;
	}

	@Override
	public Integer uphit(String Recipe_No) {
		return recipeMapper.UpdateUphit(Recipe_No);
	}

	@Override
	public boolean addMyRecipe(RecipesCartDTO cartDTO, Principal principal) {
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
	public boolean deleteMyRecipe(RecipesCartDTO cartDTO, Principal principal) {
		// recommended Number minus
		// cartDTO 의 id와 recipe_no을 함께 조회했을 때 조회가 되면 아래 문구 시행.
		int cnt = recipeMapper.checkAuthority(cartDTO);
		int count = 0;
		// 카트 dto에 존재할 경우
		if (cnt != 0) {
			recipeMapper.minusRecommendedNumber(cartDTO);
			count = recipeMapper.deleteCart(cartDTO);
		} else
			count = 0;
		return (count == 1) ? true : false;
	}

	@Override
	public boolean uploadRecipe(MultipartFile file, Map<String, Object> data, Principal principal) throws Exception {
		// 파일 처리
		System.out.println("file:" + file);
		String uploadFolder = "C:\\Users\\i\\Documents\\workspace-spring-tool-suite-4-4.14.1.RELEASE\\Team\\Team\\FDProject\\src\\main\\resources\\static\\assets\\img\\recipeImages\\";
		System.out.println("uploadFolder:" + uploadFolder);
		System.out.println("Upload File Name:" + file.getOriginalFilename());
		System.out.println("Upload File Size:" + file.getSize());

		String uploadFileName = file.getOriginalFilename();
		System.out.println("only file name:" + uploadFileName);

		String savedName = randomFileName(uploadFileName, file.getBytes());
		System.out.println("random file name:" + savedName);

		File saveFile = new File(uploadFolder, savedName);
		System.out.println("full path filename:" + uploadFolder + savedName);
		try {
			file.transferTo(saveFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("에러 발생");
		}
		// data 처리
		RecipeDTO params = new RecipeDTO();
		params.setImgFile(uploadFileName);
		params.setRandomImgFile(savedName);
		params.setTip((String) data.get("tip"));
		params.setStorage((String) data.get("storage"));
		params.setTitle((String) data.get("title"));
		params.setStep((String) data.get("step"));
		params.setFoodIngredients((String) data.get("foodIngredients"));
		// writer 지정
		params.setWriter(principal.getName());
		// recipe_no 지정
		int recipe_no = recipeMapper.getRecipeNo();
		// 변경의 경우
		if (data.get("type").equals("modify"))
			params.setRecipeNo(recipe_no);
		else { // 등록의 경우
			recipe_no += 1;
			params.setRecipeNo(recipe_no);
		}
		// upload 동작 수행.
		int count = 0;
		// 추가
		System.out.println("나 여기0");
		System.out.println("(String) data.get(\"foodIngredients\")" + data.get("foodIngredients"));
		System.out.println("(String) data.get(\"type\")" + (String) data.get("type"));
		if (data.get("type").equals("add")) {
			System.out.println("나 여기1");
			count = recipeMapper.uploadRecipe(params);
		}
		// 변경
		else if (data.get("type").equals("modify")) {
			System.out.println("나 여기2");
			count = recipeMapper.modifyRecipe(params);
		}

		System.out.println("params:" + params);
		return (count == 1) ? true : false;
	}

	@Override
	public String randomFileName(String uploadFileName, byte[] file) throws Exception {

		UUID uid = UUID.randomUUID();

		String savedName = uid.toString() + "_" + uploadFileName;
		String uploadPath = "C:\\Users\\i\\Documents\\workspace-spring-tool-suite-4-4.14.1.RELEASE\\Team\\Team\\FDProject\\src\\main\\resources\\static\\assets\\img\\recipeImages\\";
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(file, target);

		return savedName;
	}

	@Override
	public int deleteRecipe(String Recipe_No) {
		return recipeMapper.deleteYNRecipe(Recipe_No);
	}

}
