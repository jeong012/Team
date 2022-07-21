package com.fdproject.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fdproject.domain.CommentDTO;
import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.UserDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.DrugService;
import com.fdproject.service.RecipeService;
import com.fdproject.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {

	private final UserService userService;
	private final RecipeService recipeService;
	private final DrugService drugService;
	private final DiseaseService diseaseService;
	
	/** 회원 리스트 */
	@GetMapping(value="/management/userList.do")
	public String getUserList(@ModelAttribute("params") UserDTO params, Model model){
		List<UserDTO> userList = userService.getUserList(params);
		model.addAttribute("userList", userList);
		model.addAttribute("currentPageNo", params.getCurrentPageNo());
		
		return "admin/management/userList";
	}
	
	/** 회원 정보 가져오기 */
	@ResponseBody
	@PostMapping(value="/management/getUserDetail.do")
	public Map<String, Object> getUserDetail(@RequestParam(value="userNo") int userNo){
		Map<String, Object> userMap = new HashMap<>();
		UserDTO userDTO = userService.getUserDetail(userNo);
		userMap.put("userDTO", userDTO);
		
		String userDrugs = drugService.getUserDrugByAdmin(userNo);
		userMap.put("userDrugs", userDrugs);
		
		String userDiseases = diseaseService.getUserDiseaseByAdmin(userNo);
		userMap.put("userDiseases", userDiseases);

		/** 사용자 작성 레시피 조회 */
		List<RecipeDTO> userRecipeList = recipeService.getUserRecipe(userNo);
		userMap.put("userRecipeList", userRecipeList);

		/** 사용자 작성 댓글 조회 */
		List<CommentDTO> userCommentList = recipeService.getUserComment(userNo);
		userMap.put("userCommentList", userCommentList);
		
		return userMap;
	}
	
	/** 회원 삭제 */
	@ResponseBody
	@PostMapping(value="/management/deleteUser.do")
	public Map<String, Object> deleteUser(@ModelAttribute(value="params") UserDTO params){
		Map<String, Object> userMap = new HashMap<>();
		int isDeleted = userService.deleteUser(params.getUserNo());
		if(isDeleted > 0) {
			List<UserDTO> userList = userService.getUserList(params);
			userMap.put("userList", userList);
			userMap.put("result","success");
		} else {
			userMap.put("result","fail");
		}
		
		return userMap;
	}
	
	/** 레시피 리스트 */
	@GetMapping(value="/management/recipeList.do")
	public String getRecipeList(@ModelAttribute("params") RecipeDTO recipeDTO, Model model){
		List<RecipeDTO> recipeList = recipeService.getRecipeList(recipeDTO);
		model.addAttribute("recipeList", recipeList);
		
		List<DiseaseDTO> diseaseList = diseaseService.getDiseaseList();
		model.addAttribute("diseaseList", diseaseList);
		
		return "admin/management/recipeList";
	}
	
	/** 레시피 상세 페이지 */
	@GetMapping(value="/management/recipeView.do")
	public String getRecipeView(@RequestParam(value = "recipeNo", required = false) String recipeNo, Model model, Principal principal,
								@RequestParam (value="prev") String prev) {
		RecipeDTO recipeInfo = recipeService.getRecipeInfo(recipeNo, principal);
		model.addAttribute("recipeInfo", recipeInfo);

		if (recipeInfo.getFoodIngredients() != null) {
			String recipe_ingredients = recipeInfo.getFoodIngredients();
			String[] ri_split = recipe_ingredients.split("\n");
			ArrayList<String> AL_ri_split = new ArrayList<>();
			for (int i = 0; i < ri_split.length; i++) {
				AL_ri_split.add(ri_split[i]);
			}
			model.addAttribute("AL_ri_split", AL_ri_split);

			String recipeStep = recipeInfo.getStep();
			ArrayList<String> AL_rs_split = new ArrayList<>();
			String temp = recipeStep;
			boolean run = true;
			while (run) {
				String data = "";

				if (temp.indexOf("\n[") != -1) {
					int startIndex = temp.indexOf("] ") + "] ".length();
					int endIndex = temp.indexOf("\n[");

					data = temp.substring(startIndex, endIndex);
					temp = temp.substring(endIndex + 1);
				} else {
					data = temp.substring(temp.indexOf("] ") + "] ".length());
					run = false;
				}

				AL_rs_split.add(data);
			}
			model.addAttribute("AL_rs_split", AL_rs_split);
		}
		
		/** 댓글 수 가져오기 */
		int commentCnt = recipeService.getCommentCnt(Integer.parseInt(recipeNo));
		model.addAttribute("commentCnt",commentCnt);

		/** 레시피 추천 수 구하기 */
		int recommendedCnt = recipeService.getRecommendedCnt(Integer.parseInt(recipeNo));
		model.addAttribute("recommendedCnt",recommendedCnt);
		
		/** 이전 페이지 */
		model.addAttribute("prev", prev);

		return "admin/management/recipeView";
	}
	
	/** 레시피 삭제 */
	@ResponseBody
	@PostMapping(value="/deleteRecipe.do")
	public String deleteRecipe(@RequestParam("recipeNo") String recipeNo, HttpServletRequest request){
		String result = "fail";
		
		int isDeleted = recipeService.deleteRecipe(recipeNo);
		if(isDeleted == 1) {
			result = "success";
		}else {
			result = "fail";
		}
		
		return result; 
	}
	
	/** 레시피 댓글 리스트 가져오기*/
	@ResponseBody
	@PostMapping(value="/commentList.do")
	public HashMap<String, Object> getCommentList(@RequestParam("recipeNo") int recipeNo){
		List<CommentDTO> commentList = recipeService.getCommentList(recipeNo);

        HashMap<String, Object> commentMap = new HashMap<String, Object>();
        commentMap.put("commentList", commentList);
		return commentMap;
	}
	
	/** 레시피 댓글 삭제 */
	@ResponseBody
	@PostMapping(value="/deleteComment.do")
	public HashMap<String, Object> deleteComment(@ModelAttribute("params") CommentDTO commentDTO) {
		String memo = "(관리자에 의해 삭제된 댓글입니다)";
		
		HashMap<String, Object> commentMap = new HashMap<String, Object>();
        int isDeleted = recipeService.deleteComment(commentDTO, memo);
        if(isDeleted >= 1) {
			List<CommentDTO> commentList = recipeService.getCommentList(commentDTO.getRecipeNo());	
			commentMap.put("result", "success");
			commentMap.put("commentList", commentList);
		} else {
			commentMap.put("result", "fail");
		}
        
		return commentMap;
	}
}
