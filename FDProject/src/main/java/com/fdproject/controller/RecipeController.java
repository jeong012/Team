package com.fdproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.RecipeService;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired 
	private RecipeService recipeService;
	@Autowired 
	private DiseaseService diseaseService;
	
	@GetMapping(value="/list")
	public String getRecipeList(@RequestParam(value = "recipe_type", required = false) String recipe_type,
			Model model,
			// disease_Name으로 recipe disease column 조회해서 리스트로 불러오기.
			@RequestParam(value = "diseaseField", required = false) String diseaseField){		
		
		// 질병 이름 선택했을 경우 레시피 리스트 뽑아오기
		if(diseaseField != null) {
			List<RecipeDTO> Recipe_List_By_DField = recipeService.getRecipeListByDiseaseField(diseaseField);
			model.addAttribute("Recipe_List", Recipe_List_By_DField);
		}
		//Recipe_List 전부 가져오는 객체
		else {
			List<RecipeDTO> Recipe_List = recipeService.getRecipeList();		
			model.addAttribute("Recipe_List", Recipe_List);
		}
		//disease_list 전부 가져오는 객체
		List<DiseaseDTO> Disease_List_Five = diseaseService.getDiseaseListFive();
		model.addAttribute("Disease_List_Five", Disease_List_Five);		
		//System.out.println("Disease_List_Five:" + Disease_List_Five);
		
		
		
		return "recipe/list";
	}

	@GetMapping(value="/view")
	public String getRecipe(){
		return "recipe/view";
	}

	@GetMapping(value="/writeForm")
	public String getRecipeForm(){
		return "recipe/writeForm";
	}
}
