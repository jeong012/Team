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
import com.fdproject.domain.ReceipeDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.ReceipeService;

@Controller
@RequestMapping("/receipe")
public class ReceipeController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired 
	private ReceipeService receipeService;
	@Autowired 
	private DiseaseService diseaseService;
	
	@GetMapping(value="/list")
	public String getReceipeList(@RequestParam(value = "receipe_type", required = false) String receipe_type,
			Model model,
			// disease_Name으로 Receipe disease column 조회해서 리스트로 불러오기.
			@RequestParam(value = "diseaseField", required = false) String diseaseField){		
		
		// 질병 이름 선택했을 경우 레시피 리스트 뽑아오기
		if(diseaseField != null) {
			List<ReceipeDTO> Receipe_List_By_DField = receipeService.getReceipeListByDiseaseField(diseaseField);
			model.addAttribute("Receipe_List", Receipe_List_By_DField);
		}
		//Recipe_List 전부 가져오는 객체
		else {
			List<ReceipeDTO> Receipe_List = receipeService.getReceipeList();		
			model.addAttribute("Receipe_List", Receipe_List);
		}
		//disease_list 전부 가져오는 객체
		List<DiseaseDTO> Disease_List_Five = diseaseService.getDiseaseListFive();
		model.addAttribute("Disease_List_Five", Disease_List_Five);		
		//System.out.println("Disease_List_Five:" + Disease_List_Five);
		
		
		
		return "receipe/list";
	}

	@GetMapping(value="/view")
	public String getReceipe(){
		return "receipe/view";
	}

	@GetMapping(value="/writeForm")
	public String getReceipeForm(){
		return "receipe/writeForm";
	}
}
