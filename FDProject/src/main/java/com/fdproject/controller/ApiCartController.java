package com.fdproject.controller;


import java.io.File;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.domain.RecipesCartDTO;
import com.fdproject.service.DrugService;
import com.fdproject.service.RecipeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ApiCartController {
    private final DrugService drugService;
    private final RecipeService recipeService;

    @PostMapping("/addDrug")
    public @ResponseBody boolean insert1(@ModelAttribute DrugsCartDTO cartDTO) {

        return drugService.addMyDrug(cartDTO);
    }

    @PostMapping("/deleteDrug")
    public @ResponseBody boolean delete1(@ModelAttribute DrugsCartDTO cartDTO) {

        return drugService.deleteMyDrug(cartDTO);
    }
    
    @PostMapping("/addRecipe")
    public @ResponseBody boolean insert2(@ModelAttribute RecipesCartDTO cartDTO, Principal principal) {
    	cartDTO.setUserId(principal.getName());
        return recipeService.addMyRecipe(cartDTO, principal);
    }

    @PostMapping("/deleteRecipe")
    public @ResponseBody boolean delete2(@ModelAttribute RecipesCartDTO cartDTO, Principal principal) {
    	cartDTO.setUserId(principal.getName());
        return recipeService.deleteMyRecipe(cartDTO, principal);
    }
	
}
