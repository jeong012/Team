package com.fdproject.controller;

import com.fdproject.constant.Method;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.paging.Criteria;
import com.fdproject.service.DrugService;
import com.fdproject.util.GrammerUtils;
import com.fdproject.util.UiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "drug")
@RequiredArgsConstructor
public class DrugController extends UiUtils {

    private final DrugService drugService;

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
    
    @GetMapping(value = "/myview.do")
    public String getMyDrug(@ModelAttribute(value = "params") DrugDTO params, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "no", required = false) int drugNo, Model model) {
        
    	if (GrammerUtils.isStringEmpty(id) == true) {
            return showMessageWithRedirect("접근 권한이 없습니다.", "/drug/list.do", Method.GET, null, model);
        }
        DrugDTO mydrug = drugService.getDrug(drugNo);
        model.addAttribute("mydrug", mydrug);
        return "drug/myview";
    }

    @GetMapping(value ="/pharmacy.do")
    public String getFindPharmacy() {
    	
        return "drug/find_pharmacy";
    }
    
    @GetMapping(value="/store.do")
	public String getFindStore(@ModelAttribute("params") DrugDTO params, @RequestParam(value = "id", required = false) String id, Model model){
    	
    	List<DrugDTO> housedrugList = drugService.getHouseDrugList(params);
        model.addAttribute("housedrugList", housedrugList);
        model.addAttribute("id", id);
        
		return "drug/find_store";
	}

}
