package com.fdproject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fdproject.domain.CommentDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.RecipeRecommendedDTO;
import com.fdproject.domain.RecipesCartDTO;

@Mapper
public interface RecipeMapper {
	List<RecipeDTO> selectRecipeList(RecipeDTO params);
	List<RecipeDTO> selectWriterRecipeList(RecipeDTO params);
	List<RecipeDTO> selectMyWriteRecipeList(RecipeDTO params);
	RecipeDTO selectRecipeInfo(String Recipe_no);
    Integer UpdateUphit(String Recipe_no);
	int selectRecipeTotalCount(RecipeDTO params);
	int selectWriterRecipeTotalCount(RecipeDTO params);
	int selectMyWriterRecipeTotalCount(RecipeDTO params);
	RecipesCartDTO selectMyRecipe(RecipesCartDTO cartDTO);
	int addCart(RecipesCartDTO cartDTO);
	int deleteCart(RecipesCartDTO cartDTO);
	List<RecipesCartDTO> myRecipeList(String id);
	List<RecipeDTO> getMyRecipeList(RecipeDTO params);
	int selectMyRecipeTotalCount(RecipeDTO params);
	int updateCartCount(RecipesCartDTO params);
	int minusCartCount(RecipesCartDTO params);
	int checkAuthority(RecipesCartDTO params);
	int uploadRecipe(RecipeDTO params);
	int modifyRecipe(RecipeDTO params);
	int getRecipeNo();	 
	int deleteYNRecipe(String Recipe_no);
		
	/** 레시피 댓글 리스트 가져오기*/
	List<CommentDTO> getCommentList(int recipeNo);

	/** 레시피 댓글 등록*/
	int postComment(CommentDTO commentDTO);
	
	/** 레시피 댓글 삭제*/
	int deleteComment(@Param("commentNo") int commentNo, @Param("memo") String memo);
	
	/** 레시피 삭제 후 댓글 삭제*/
	int deleteCommentByRecipe(int recipeNo);
	
	/** 레시피 댓글 가져오기*/
	CommentDTO getComment(int commentNo);
	
	/** 레시피 답변 등록*/
	int replyComment(CommentDTO commentDTO);
	
	/** 레시피 댓글 간격 차이 계산*/
	String getPosGap(@Param("parentNo") int parentrNo, @Param("depth") int depth);

	/** 레시피 부모 댓글 개수, 첫 위치 구하기*/
	Map<String, Object> getPos(@Param("parentNo") int parentrNo, @Param("depth") int depth);
	
	/** 레시피 대댓글 후 댓글 포지션 수정*/
	int updatePlusPos(CommentDTO commentDTO);

	/** 레시피 댓글 수 구하기*/
	int getCommentCnt(int recipeNo);
	
	/** 레시피 댓글 수정*/
	int updateComment(CommentDTO commentDTO);
	
	/** 레시피 추천 수 구하기*/
	int getRecommendedCnt(int recipeNo);
	
	/** 레시피 추천 여부 조회*/
	RecipeRecommendedDTO getIsRecommended(RecipeRecommendedDTO recipeRecommendedDTO);

	/** 레시피 추천 추가*/
	int addRecommended(RecipeRecommendedDTO recipeRecommendedDTO);
	
	/** 레시피 추천 삭제*/
	int removeRecommended(RecipeRecommendedDTO recipeRecommendedDTO);
	
	/** 사용자 작성 레시피 조회 */
	List<RecipeDTO> getUserRecipe(int userNo);
	
	/** 사용자 작성 댓글 조회 */
	List<CommentDTO> getUserComment(int userNo);
	
}