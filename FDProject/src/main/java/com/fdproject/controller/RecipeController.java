package com.fdproject.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fdproject.domain.CommentDTO;
import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.RecipeRecommendedDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.RecipeService;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

	// private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RecipeService recipeService;
	@Autowired
	private DiseaseService diseaseService;

	@GetMapping(value = "/list.do")
	public String getRecipeList(@ModelAttribute("params") RecipeDTO params, Model model, Principal principal) {
		// 로그인 세션이 있으면
		if (principal != null) {
			List<DiseaseDTO> list = diseaseService.getUserDiseaseList(principal);
			model.addAttribute("user_disease_list", list);
			System.out.println("user_disease_list:" + list);
		}
		// 레시피 리스트 뽑아오기
		List<RecipeDTO> Recipe_List = recipeService.getRecipeList(params);
		model.addAttribute("Recipe_List", Recipe_List);
		// System.out.println("Recipe_List:" + Recipe_List);
		// disease_list 상위 5개 가져오는 객체
		List<DiseaseDTO> Disease_List = diseaseService.getDiseaseList();
		model.addAttribute("Disease_List", Disease_List);
		System.out.println("Disease_List:" + Disease_List);
		// System.out.println("Disease_List_Five:" + Disease_List_Five);

		return "recipe/list";
	}

	@GetMapping(value = "/view.do")
	public String getRecipe(@RequestParam(value = "Recipe_No", required = false) String Recipe_No, Principal principal,
			Model model) {

		// recipe_info
		RecipeDTO Recipe_info = recipeService.getRecipeInfo(Recipe_No, principal);
		model.addAttribute("Recipe_info", Recipe_info);
		System.out.println("Recipe_info" + Recipe_info);
		// foodIngredients split해서 배열에 차곡차곡 넣음
		if (Recipe_info.getFoodIngredients() != null) {
			String recipe_ingredients = Recipe_info.getFoodIngredients();
			String[] ri_split = recipe_ingredients.split("\n");
			ArrayList<String> AL_ri_split = new ArrayList<>();
			for (int i = 0; i < ri_split.length; i++) {
				AL_ri_split.add(ri_split[i]);
			}
			model.addAttribute("AL_ri_split", AL_ri_split);

			// step '][' 기준으로 자르기
			// recipe_step 가져오기
			String recipe_step = Recipe_info.getStep();
			// model로 넘길 arrayList 생성
			ArrayList<String> AL_rs_split = new ArrayList<>();
			// System.out.println(recipe_step);
			String temp = recipe_step;
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

				System.out.println(data);
				// data arraylist에 넣기
				AL_rs_split.add(data);
			}
			model.addAttribute("AL_rs_split", AL_rs_split);
		}

		// 조회수 관련 - 구현 완료
		recipeService.uphit(Recipe_No);

		/** 댓글 수 가져오기 */
		int commentCnt = recipeService.getCommentCnt(Integer.parseInt(Recipe_No));
		model.addAttribute("commentCnt", commentCnt);

		/** 레시피 추천 수 구하기 */
		int recommendedCnt = recipeService.getRecommendedCnt(Integer.parseInt(Recipe_No));
		model.addAttribute("recommendedCnt", recommendedCnt);

		/** 레시피 추천 여부 조회 */
		RecipeRecommendedDTO recipeRecommendedDTO = new RecipeRecommendedDTO();
		recipeRecommendedDTO.setRecipeNo(Integer.parseInt(Recipe_No));
		recipeRecommendedDTO.setUserId(principal.getName());

		boolean isRecommended = recipeService.getIsRecommended(recipeRecommendedDTO);
		model.addAttribute("isRecommended", isRecommended);

		return "recipe/view";
	}

	@GetMapping(value = "/writeForm.do")
	public String getRecipeForm(@RequestParam(value = "Recipe_No", required = false) String Recipe_No, Model model,
			Principal principal) {
		if (Recipe_No != null) {
			// recipe_info
			RecipeDTO Recipe_info = recipeService.getRecipeInfo(Recipe_No, principal);
			model.addAttribute("Recipe_info", Recipe_info);
			System.out.println("Recipe_info" + Recipe_info);
			// foodIngredients split해서 배열에 차곡차곡 넣음
			if (Recipe_info.getFoodIngredients() != null) {
				String recipe_ingredients = Recipe_info.getFoodIngredients();
				String[] ri_split = recipe_ingredients.split("\n");
				ArrayList<String> AL_ri_split = new ArrayList<>();
				for (int i = 0; i < ri_split.length; i++) {
					AL_ri_split.add(ri_split[i]);
				}
				model.addAttribute("AL_ri_split", AL_ri_split);

				// step '][' 기준으로 자르기
				// recipe_step 가져오기
				String recipe_step = Recipe_info.getStep();
				// model로 넘길 arrayList 생성
				ArrayList<String> AL_rs_split = new ArrayList<>();
				// System.out.println(recipe_step);
				String temp = recipe_step;
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

					System.out.println(data);
					// data arraylist에 넣기
					AL_rs_split.add(data);
				}
				model.addAttribute("AL_rs_split", AL_rs_split);
			}
		}
		return "recipe/writeForm";
	}

	@ResponseBody
	@PostMapping(value = "/add.do")
	public String addRecipe(@RequestPart(value = "File", required = false) MultipartFile file,
			@RequestPart(value = "Data") Map<String, Object> data, Principal principal) throws Exception {

		System.out.println("file:" + file);
		System.out.println("data:" + data);

		recipeService.uploadRecipe(file, data, principal);
		return "ok";
	}

	@ResponseBody
	@PostMapping(value = "/delete.do")
	public String deleteRecipe(@RequestBody String Recipe_No) {
		String real_Recipe_No[] = Recipe_No.split("=");
		System.out.println("Recipe_No:" + real_Recipe_No[0]);
		recipeService.deleteRecipe(real_Recipe_No[0]);
		return "ok";
	}

	/** 레시피 댓글 리스트 가져오기 */
	@ResponseBody
	@PostMapping(value = "/comment/getList.do")
	public HashMap<String, Object> getCommentList(@RequestParam("recipeNo") int recipeNo) {
		List<CommentDTO> commentList = recipeService.getCommentList(recipeNo);

		HashMap<String, Object> commentMap = new HashMap<String, Object>();
		commentMap.put("commentList", commentList);
		return commentMap;
	}

	/** 레시피 댓글 등록 */
	@ResponseBody
	@PostMapping(value = "/comment/post.do")
	public HashMap<String, Object> postComment(@ModelAttribute("params") CommentDTO commentDTO) {
		HashMap<String, Object> commentMap = new HashMap<String, Object>();
		int isInserted = recipeService.postComment(commentDTO);
		if (isInserted == 1) {
			List<CommentDTO> commentList = recipeService.getCommentList(commentDTO.getRecipeNo());
			commentMap.put("result", "success");
			commentMap.put("commentList", commentList);
		} else {
			commentMap.put("result", "fail");
		}

		return commentMap;
	}

	/** 레시피 댓글 삭제 */
	@ResponseBody
	@PostMapping(value = "/comment/delete.do")
	public HashMap<String, Object> deleteComment(@ModelAttribute("params") CommentDTO commentDTO) {
		HashMap<String, Object> commentMap = new HashMap<String, Object>();

		String content = "(작성자에 의해 삭제된 댓글입니다)";
		int isDeleted = recipeService.deleteComment(commentDTO, content);
		if (isDeleted >= 1) {
			List<CommentDTO> commentList = recipeService.getCommentList(commentDTO.getRecipeNo());
			commentMap.put("result", "success");
			commentMap.put("commentList", commentList);
		} else {
			commentMap.put("result", "fail");
		}

		return commentMap;
	}

	/** 레시피 댓글 가져오기 */
	@ResponseBody
	@PostMapping(value = "/comment/get.do")
	public HashMap<String, Object> getComment(@RequestParam("commentNo") int commentNo) {
		HashMap<String, Object> commentMap = new HashMap<String, Object>();
		CommentDTO commentDTO = recipeService.getComment(commentNo);
		commentMap.put("commentDTO", commentDTO);

		return commentMap;
	}

	/** 레시피 답변 등록 */
	@ResponseBody
	@PostMapping(value = "/comment/reply.do")
	public HashMap<String, Object> replyComment(@ModelAttribute("params") CommentDTO commentDTO) {
		HashMap<String, Object> commentMap = new HashMap<String, Object>();

		CommentDTO parentDTO = recipeService.getComment(commentDTO.getParentNo());
		commentDTO.setRef(parentDTO.getRef());
		commentDTO.setDepth(parentDTO.getDepth() + 1);

		String gap = recipeService.getPosGap(commentDTO.getParentNo(), commentDTO.getDepth());
		if (gap == null) {
			commentDTO.setPos(parentDTO.getPos());
		} else if (gap.equals("0")) {
			commentDTO.setPos(parentDTO.getPos() + 1);
		} else {
			Map<String, Object> map = recipeService.getPos(commentDTO.getParentNo(), commentDTO.getDepth());
			String pos = map.get("POS").toString();
			String countParentCount = map.get("COUNTPARENTCOUNT").toString();

			if (gap.equals("1")) {
				commentDTO.setPos(parentDTO.getPos() + Integer.parseInt(countParentCount));
			} else {
				commentDTO.setPos(Integer.parseInt(pos) + Integer.parseInt(gap));
			}
		}

		int isInserted = recipeService.replyComment(commentDTO);
		if (isInserted >= 1) {
			List<CommentDTO> commentList = recipeService.getCommentList(commentDTO.getRecipeNo());
			commentMap.put("result", "success");
			commentMap.put("commentList", commentList);
		} else {
			commentMap.put("result", "fail");
		}

		return commentMap;
	}

	/** 레시피 댓글 수정 */
	@ResponseBody
	@PostMapping(value = "/comment/update.do")
	public HashMap<String, Object> updateComment(@ModelAttribute("params") CommentDTO commentDTO) {
		HashMap<String, Object> commentMap = new HashMap<String, Object>();
		int isUpdated = recipeService.updateComment(commentDTO);
		if (isUpdated == 1) {
			List<CommentDTO> commentList = recipeService.getCommentList(commentDTO.getRecipeNo());
			commentMap.put("result", "success");
			commentMap.put("commentList", commentList);
		} else {
			commentMap.put("result", "fail");
		}

		return commentMap;
	}

	/** 레시피 추천 추가 */
	@ResponseBody
	@PostMapping(value = "/recommended/add.do")
	public HashMap<String, Object> addRecommended(@ModelAttribute("params") RecipeRecommendedDTO recipeRecommendedDTO) {
		HashMap<String, Object> recommendedMap = new HashMap<String, Object>();
		int isInserted = recipeService.addRecommended(recipeRecommendedDTO);
		if (isInserted == 1) {
			int recommendedCnt = recipeService.getRecommendedCnt(recipeRecommendedDTO.getRecipeNo());
			recommendedMap.put("result", "success");
			recommendedMap.put("recommendedCnt", recommendedCnt);
		} else {
			recommendedMap.put("result", "fail");
		}

		return recommendedMap;
	}

	/** 레시피 추천 삭제 */
	@ResponseBody
	@PostMapping(value = "/recommended/remove.do")
	public HashMap<String, Object> removeRecommended(
			@ModelAttribute("params") RecipeRecommendedDTO recipeRecommendedDTO) {
		HashMap<String, Object> recommendedMap = new HashMap<String, Object>();
		int isInserted = recipeService.removeRecommended(recipeRecommendedDTO);
		if (isInserted == 1) {
			int recommendedCnt = recipeService.getRecommendedCnt(recipeRecommendedDTO.getRecipeNo());
			recommendedMap.put("result", "success");
			recommendedMap.put("recommendedCnt", recommendedCnt);
		} else {
			recommendedMap.put("result", "fail");
		}

		return recommendedMap;
	}

}