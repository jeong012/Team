package com.fdproject.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdproject.constant.Method;
import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.DrugService;
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
			 	
	            return showMessageWithRedirect("접근 권한이 없습니다.", "/", Method.GET, null, model);
	        } else {
	            List<DiseaseDTO> diseaseList = diseaseService.getDiseaseList(id, params);
	            model.addAttribute("diseaseList", diseaseList);
	            model.addAttribute("id", id);
	        }
		 
		return "mypage/diseaselist";
	}
	
	@GetMapping(value="/drug.do")
	public String getDrugList(@ModelAttribute("params") DrugDTO params, @RequestParam(value = "id", required = false) String id, Model model){
		
		if (GrammerUtils.isStringEmpty(id) == true) {		
            return showMessageWithRedirect("접근 권한이 없습니다.", "/", Method.GET, null, model);
        } else {
        	List<DrugDTO> userdrugList = drugService.getUserDrugList(id, params);
            model.addAttribute("userdrugList", userdrugList);
            model.addAttribute("id", id);
        }
		return "mypage/druglist";
	}

}
