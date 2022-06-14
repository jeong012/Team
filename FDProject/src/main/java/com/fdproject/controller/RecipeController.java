package com.fdproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
	
	@GetMapping(value="/list")
	public String getRecipeList(){
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
