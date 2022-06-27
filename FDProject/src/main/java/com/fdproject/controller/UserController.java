package com.fdproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.DrugService;
import com.fdproject.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
    private final DiseaseService diseaseService;
    private final DrugService drugService;
    private final UserService userService;

	@GetMapping(value="/joinForm.do")
	public String getJoinForm(){
		return "user/joinForm";
	}

	@PostMapping(value="/joinForm2.do")
	public String getJoinForm2(){
		return "user/joinForm2";
	}
	
	@GetMapping(value="/loginForm.do")
	public String getLoginForm(){
		return "user/loginForm";
	}

    /** 회원가입 - 질병 리스트 조회 사용*/
    @ResponseBody
    @GetMapping("/join/getDiseaseList.do")
    public HashMap<String, Object> getJoinDiseaseList(Model model) {
        List<DiseaseDTO> diseaseList = diseaseService.getJoinDiseaseList();
        
        HashMap<String, Object> diseaseMap = new HashMap<String, Object>();
        diseaseMap.put("diseaseList", diseaseList);
        return diseaseMap;
    }

    /** 회원가입 - 약 리스트 조회 사용*/
    @ResponseBody
    @GetMapping("/join/getDrugList.do")
    public HashMap<String, Object> getJoinDrugList(Model model) {
        List<DrugDTO> drugList = drugService.getJoinDrugList();
        
        HashMap<String, Object> drugMap = new HashMap<String, Object>();
        drugMap.put("drugList", drugList);
        return drugMap;
    }
    
    @ResponseBody
    @PostMapping("/join.do")
    public HashMap<String, Object> join(@RequestBody String params) {

    	int isInserted = 0;
    	ArrayList<UserDiseaseDTO> userDiseaseList = new ArrayList<>();
    	ArrayList<UserDrugDTO> userDrugList = new ArrayList<>();
    	
		try {
	    	JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(params);

	        JSONArray diseaseArr = (JSONArray) jsonObj.get("diseaseMap");
	        for (Object diseaseObj : diseaseArr) {
	        	UserDiseaseDTO userDiseaseDTO = new UserDiseaseDTO();
	        	
		        int diseaseNo = Integer.parseInt(((JSONObject)diseaseObj).get("diseaseNo").toString());
//		        String diseaseName = ((JSONObject)diseaseObj).get("diseaseName").toString();
	        	userDiseaseDTO.setDiseaseNo(diseaseNo);
	        	
	        	userDiseaseList.add(userDiseaseDTO);
	        }

	        JSONArray drugArr = (JSONArray) jsonObj.get("drugMap");
	        for (Object drugObj : drugArr) {
	        	UserDrugDTO userDrugDTO = new UserDrugDTO();
	        	
		        int drugNo = Integer.parseInt(((JSONObject)drugObj).get("drugNo").toString());
//		        String drugName = ((JSONObject)drugObj).get("drugName").toString();
		        userDrugDTO.setDrugNo(drugNo);
	        	
		        userDrugList.add(userDrugDTO);
	        }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
        isInserted = userService.joinUser(userDiseaseList, userDrugList);
        if(userDiseaseList.size() == 0 && userDrugList.size() == 0) {
        	isInserted = 1;
        }
   
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
        if (isInserted == 1){
        	resultMap.put("result", "success");
        }
        else {
        	resultMap.put("result", "fail");
        }
    	
    	return resultMap;
    }
}
