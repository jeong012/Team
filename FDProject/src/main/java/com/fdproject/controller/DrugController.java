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

import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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

    @RequestMapping(value = "autoSearch",
                    method = {RequestMethod.GET},
                    produces = "application/json; charset=utf8")
    @ResponseBody
    public void search(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String searchValue = request.getParameter("searchValue");
        JsonArray arrayObj = drugService.getSearchKeyword(searchValue);
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.print(arrayObj);
        pw.flush();
        pw.close();
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
