package com.fdproject.controller;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.domain.RecipesCartDTO;
import com.fdproject.service.DrugService;
import com.fdproject.service.RecipeService;
import com.fdproject.util.GrammerUtils;
import com.fdproject.util.UiUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ApiCartController extends UiUtils {
    private final DrugService drugService;
    private final RecipeService recipeService;

    @PostMapping("/addDrug.do")
    public @ResponseBody boolean insert1(@ModelAttribute DrugsCartDTO cartDTO, Principal principal, Model model) {

        String id = principal.getName();
        if (GrammerUtils.isStringEmpty(id) == true) {
            return false;
        }

        return drugService.addMyDrug(id, cartDTO);
    }

    @PostMapping("/deleteDrug.do")
    public @ResponseBody boolean delete1(@ModelAttribute DrugsCartDTO cartDTO, Principal principal) {

        String id = principal.getName();
        if (GrammerUtils.isStringEmpty(id) == true) {
            return false;
        }

        return drugService.deleteMyDrug(cartDTO);
    }
    
    @PostMapping("/addRecipe.do")
    public @ResponseBody boolean insert2(@ModelAttribute RecipesCartDTO cartDTO, Principal principal) {
    	cartDTO.setUserId(principal.getName());
        return recipeService.addMyRecipe(cartDTO, principal);
    }

    @PostMapping("/deleteRecipe.do")
    public @ResponseBody boolean delete2(@ModelAttribute RecipesCartDTO cartDTO, Principal principal) {
    	cartDTO.setUserId(principal.getName());
        return recipeService.deleteMyRecipe(cartDTO, principal);
    }
	
}
