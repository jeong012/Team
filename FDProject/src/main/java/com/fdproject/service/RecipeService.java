package com.fdproject.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fdproject.domain.CommentDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.RecipeRecommendedDTO;
import com.fdproject.domain.RecipesCartDTO;

public interface RecipeService {
	
	public List<RecipeDTO> getRecipeList(RecipeDTO params); //recipe list
	
	public List<RecipeDTO> getMyRecipeList(RecipeDTO params, Principal principal); // my recipe list		
	
	public RecipeDTO getRecipeInfo(String Recipe_info, Principal principal);
	
	public Integer uphit(String Recipe_No);
	
	public boolean addMyRecipe(RecipesCartDTO cartDTO, Principal principal);
	
	public boolean deleteMyRecipe(RecipesCartDTO cartDTO, Principal principal);
	
	public boolean uploadRecipe(MultipartFile file, Map<String, Object> data, Principal principal) throws Exception;
	
	public String randomFileName(String uploadFileName,byte[] file) throws Exception;
	
	public int deleteRecipe(String Recipe_No);

	/** 레시피 댓글 리스트 가져오기*/
	public List<CommentDTO> getCommentList(int recipeNo);

	/** 레시피 댓글 등록*/
	public int postComment(CommentDTO commentDTO);
	
	/** 레시피 댓글 삭제*/
	public int deleteComment(CommentDTO commentDTO);
	
	/** 레시피 댓글 가져오기*/
	public CommentDTO getComment(int commentNo);
	
	/** 레시피 답변 등록*/
	public int replyComment(CommentDTO commentDTO);	
	
	/** 레시피 댓글 간격 차이 계산*/
	public String getPosGap(int parentrNo, int depth);

	/** 레시피 부모 댓글 개수, 첫 위치 구하기*/
	public Map<String, Object> getPos(int parentrNo, int depth);
	
	/** 레시피 댓글 수 구하기*/
	public int getCommentCnt(int recipeNo);
	
	/** 레시피 댓글 수정*/
	public int updateComment(CommentDTO commentDTO);
	
	/** 레시피 추천 수 구하기*/
	public int getRecommendedCnt(int recipeNo);
	
	/** 레시피 추천 여부 조회*/
	public boolean getIsRecommended(RecipeRecommendedDTO recipeRecommendedDTO);

	/** 레시피 추천 추가*/
	public int addRecommended(RecipeRecommendedDTO recipeRecommendedDTO);
	
	/** 레시피 추천 삭제*/
	int removeRecommended(RecipeRecommendedDTO recipeRecommendedDTO);
	
}
