package com.fdproject.controller;

import java.util.List;

import com.fdproject.service.DrugService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.util.GrammerUtils;
import com.fdproject.util.UiUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="mypage")
@RequiredArgsConstructor
public class MyPageController extends UiUtils {

	private final DiseaseService diseaseService;

	private final DrugService drugService;
	
	@GetMapping(value="/disease.do")
	public String getDiseaseList(@ModelAttribute("params") DiseaseDTO params, @RequestParam(value = "id", required = false) String id, Model model){
		
		 if (GrammerUtils.isStringEmpty(id) == true) {
			 	id = "test";
			 	List<DiseaseDTO> diseaseList = diseaseService.getDiseaseList(id, params);
	            model.addAttribute("diseaseList", diseaseList);
	            model.addAttribute("id", id);
	            //return "redirect:/";
	        } else {
	            List<DiseaseDTO> diseaseList = diseaseService.getDiseaseList(id, params);
	            model.addAttribute("diseaseList", diseaseList);
	            model.addAttribute("id", id);
	        }
		 
		return "mypage/diseaselist";
	}
	
	@GetMapping(value="/drug.do")
	public String getDrugList(@ModelAttribute(value = "params") DrugDTO params, Model model){

		List<DrugDTO> drugList = drugService.getMyDrugList(params);
		model.addAttribute("drugList", drugList);

		return "mypage/druglist";
	}

	@GetMapping(value = "/myDrug.do")
	public String getMyDrugList(@ModelAttribute(value = "params") DrugDTO params, Model model) {
		List<DrugDTO> drugList = drugService.getMyDrugList(params);
		model.addAttribute("drugList", drugList);
		return "mypage/myDrugList";
	}
	

}
