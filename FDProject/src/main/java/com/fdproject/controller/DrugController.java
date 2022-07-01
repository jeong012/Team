package com.fdproject.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fdproject.constant.Method;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.service.DrugService;
import com.fdproject.util.GrammerUtils;
import com.fdproject.util.UiUtils;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "drug")
@RequiredArgsConstructor
public class DrugController extends UiUtils {

    private final DrugService drugService;

    //약 리스트
    @GetMapping(value = "/list.do")
    public String getDrugList(@ModelAttribute("params") DrugDTO params, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "takeYn", required = false) String takeYn, Model model) {
  
    	if (GrammerUtils.isStringEmpty(id) == true) {
            List<DrugDTO> drugList = drugService.getDrugList(id, params, takeYn);
            model.addAttribute("drugList", drugList);
            return "drug/list";
        }
        List<DrugDTO> drugList = drugService.getDrugList(id, params, takeYn);
        model.addAttribute("drugList", drugList);
        model.addAttribute("id", id);

        return "drug/list";
    }

    //약 상세정보
    @GetMapping(value = "/view.do")
    public String getDrug(@ModelAttribute(value = "params") DrugDTO params, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "takeYn", required = false) String takeYn, @RequestParam(value = "no", required = false) int drugNo, Model model) {
    	
    	if (GrammerUtils.isStringEmpty(id) == true) {
            return showMessageWithRedirect("접근 권한이 없습니다.", "/drug/list.do", Method.GET, null, model);
        }
        DrugDTO drug = drugService.getDrug(drugNo);
        model.addAttribute("drug", drug);
        DrugsCartDTO drugsCartDTO = drugService.getMyDrug(drugNo);
        model.addAttribute("myDrug", drugsCartDTO);

        return "drug/view";
    }
    
    //회원가입시 입력한 약 정보
    @GetMapping(value = "/myview.do")
    public String getMyDrug(@ModelAttribute(value = "params") DrugDTO params, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "no", required = false) int drugNo, Model model) {
        
    	if (GrammerUtils.isStringEmpty(id) == true) {
            return showMessageWithRedirect("로그인이 필요한 서비스 입니다.", "/user/loginForm.do", Method.GET, null, model);
        }
        DrugDTO mydrug = drugService.getDrug(drugNo);
        model.addAttribute("mydrug", mydrug);
        
        return "drug/myview";
    }

    @GetMapping(value = "/search")
    public @ResponseBody String search() {
        List<String> keywords = drugService.getSearchKeyword();

        Gson gson = new Gson();
        return gson.toJson(keywords);
    }

    //내 주변 약국
    @GetMapping(value ="/pharmacy.do")
    public String getFindPharmacy() {
    	
        return "drug/find_pharmacy";
    }
    
    //상비약 리스트
    @GetMapping(value="/store.do")
	public String getFindStore(@ModelAttribute("params") DrugDTO params, @RequestParam(value = "id", required = false) String id, Model model){
    	id="[Manager]";
    	List<DrugDTO> housedrugList = drugService.getHouseDrugList(params);
        model.addAttribute("housedrugList", housedrugList);
        model.addAttribute("id", id);
        
		return "drug/find_store";
	}

}
