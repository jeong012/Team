package com.fdproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdproject.service.RecipeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final RecipeService userService;
	private final RecipeService recipeService;
	
	/** 회원 관리 */
	@GetMapping(value="/userManagement.do")
	public String getUserList(){
		return "admin/management/user";
	}
	
	/** 레시피 및 댓글 관리 */
	@GetMapping(value="/recipeManagement.do")
	public String getRecipeList(){
		return "admin/management/recipe";
	}
	
}
