package com.fdproject.service;

import java.io.File;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fdproject.domain.CommentDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.RecipeRecommendedDTO;
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
		// Cart Count plus
		recipeMapper.updateCartCount(cartDTO);
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
	@Transactional
	public boolean deleteMyRecipe(RecipesCartDTO cartDTO, Principal principal) {
		// CartCount minus
		// cartDTO 의 id와 recipe_no을 함께 조회했을 때 조회가 되면 아래 문구 시행.
		int cnt = recipeMapper.checkAuthority(cartDTO);
		int count = 0;
		// 카트 dto에 존재할 경우
		if (cnt != 0) {
			recipeMapper.minusCartCount(cartDTO);
			count = recipeMapper.deleteCart(cartDTO);
		} else {
			count = 0;
		}
		
		/** 레시피 삭제시 레시피 관련 댓글 삭제*/
		recipeMapper.deleteCommentByRecipe(cartDTO.getRecipeNo());
		
		return (count == 1) ? true : false;
	}

	@Override
	public boolean uploadRecipe(MultipartFile file, Map<String, Object> data, Principal principal) throws Exception {
		// 파일 처리
		String uploadFolder = "C:\\Users\\i\\Documents\\workspace-spring-tool-suite-4-4.14.1.RELEASE\\Team\\Team\\FDProject\\src\\main\\resources\\static\\assets\\img\\recipeImages\\";
		String uploadFileName = file.getOriginalFilename();
		String savedName = randomFileName(uploadFileName, file.getBytes());

		File saveFile = new File(uploadFolder, savedName);
		try {
			file.transferTo(saveFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
		if (data.get("type").equals("add")) {
			count = recipeMapper.uploadRecipe(params);
		}
		// 변경
		else if (data.get("type").equals("modify")) {
			count = recipeMapper.modifyRecipe(params);
		}

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

	/** 레시피 댓글 리스트 가져오기*/
	@Override
	public List<CommentDTO> getCommentList(int recipeNo) {
		List<CommentDTO> commentList = recipeMapper.getCommentList(recipeNo);
		return commentList;
	}

	/** 레시피 댓글 등록*/
	@Override
	public int postComment(CommentDTO commentDTO) {
		int isInserted = recipeMapper.postComment(commentDTO);
		
		return isInserted;
	}

	/** 레시피 댓글 삭제*/
	@Override
    @Transactional
	public int deleteComment(CommentDTO commentDTO, String content) {
		int isDeleted = recipeMapper.deleteComment(commentDTO.getCommentNo(), content);
		
		return isDeleted;
	}

	/** 레시피 댓글 가져오기*/
	@Override
	public CommentDTO getComment(int commentNo) {
		CommentDTO commentDTO = recipeMapper.getComment(commentNo);
		return commentDTO;
	}

	@Override
    @Transactional
	public int replyComment(CommentDTO commentDTO) {
		/** 레시피 대댓글 후 댓글 포지션 수정*/
		int isUpdated = recipeMapper.updatePlusPos(commentDTO);
		
		/** 레시피 답변 등록*/
		int isInserted = recipeMapper.replyComment(commentDTO);
		
		return isUpdated + isInserted;
	}

	/** 레시피 댓글 간격 차이 계산*/
	@Override
	public String getPosGap(int parentrNo, int depth) {
		String gap = recipeMapper.getPosGap(parentrNo, depth);
		return gap;
	}

	/** 레시피 부모 댓글 개수, 첫 위치 구하기*/
	public Map<String, Object> getPos(int parentrNo, int depth) {
		Map<String, Object> map = recipeMapper.getPos(parentrNo, depth);
		return map;
	}

	/** 레시피 댓글 수 구하기*/
	@Override
	public int getCommentCnt(int recipeNo) {
		int commentCount = recipeMapper.getCommentCnt(recipeNo);
		return commentCount;
	}
	
	/** 레시피 댓글 수정*/
	@Override
	public int updateComment(CommentDTO commentDTO) {
		int isUpdated = recipeMapper.updateComment(commentDTO);
		return isUpdated;
	}

	/** 레시피 추천 수 구하기*/
	@Override
	public int getRecommendedCnt(int recipeNo) {
		int getRecommendedCnt = recipeMapper.getRecommendedCnt(recipeNo);
		return getRecommendedCnt;
	}

	/** 레시피 추천 여부 조회*/
	@Override
	public boolean getIsRecommended(RecipeRecommendedDTO recipeRecommendedDTO) {
		boolean isRecommended = false;
		if(recipeMapper.getIsRecommended(recipeRecommendedDTO) == null) {
			isRecommended = false;
		} else {
			isRecommended = true;
		}
		return isRecommended;
	}

	/** 레시피 추천 추가*/
	@Override
	public int addRecommended(RecipeRecommendedDTO recipeRecommendedDTO) {
		int isInserted = recipeMapper.addRecommended(recipeRecommendedDTO);
		return isInserted;
	}

	/** 레시피 추천 삭제*/
	@Override
	public int removeRecommended(RecipeRecommendedDTO recipeRecommendedDTO) {
		int isDeleted = recipeMapper.removeRecommended(recipeRecommendedDTO);
		return isDeleted;
	}

	/** 사용자 작성 레시피 조회 */
	@Override
	public List<RecipeDTO> getUserRecipe(int userNo) {
		List<RecipeDTO> userRecipeList = recipeMapper.getUserRecipe(userNo);
		return userRecipeList;
	}
	
	/** 사용자 작성 댓글 조회 */
	@Override
	public List<CommentDTO> getUserComment(int userNo) {
		List<CommentDTO> userCommentList = recipeMapper.getUserComment(userNo);
		return userCommentList;
	}
	
}