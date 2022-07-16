package com.fdproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.OAuth2UserDTO;
import com.fdproject.domain.UserDTO;
import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.DrugService;
import com.fdproject.service.MessageService;
import com.fdproject.service.UserService;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
    private final DiseaseService diseaseService;
    private final DrugService drugService;
    private final UserService userService;
	private final MessageService messageService;

	@GetMapping(value="/joinForm.do")
	public String getJoinForm(){
		return "user/joinForm";
	}

	@ResponseBody
	@PostMapping(value="/joinForm2.do")	
	public ModelAndView getJoinForm2(@RequestParam("userDTO") String userDTOJson, ModelAndView mav) {
		mav.addObject("userDTO", userDTOJson);
        mav.setViewName("user/joinForm2");
		
        return mav;
	}
	
	@GetMapping(value="/loginForm.do")
	public String getLoginForm(Model model, HttpSession httpSession){
		if(httpSession.getAttribute("oAuth2User") != null) {
			httpSession.removeAttribute("oAuth2User");
			httpSession.invalidate();	
		}
		
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userDTO", userDTO);
		return "user/loginForm";
	}
	
	@GetMapping(value="/login/error.do")
	public String getLoginError(Model model, HttpSession httpSession){
		if(httpSession.getAttribute("oAuth2User") != null) {
			httpSession.removeAttribute("oAuth2User");
			httpSession.invalidate();	
		}
		
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
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

    /** OAuth2 접근 경로 확인 */
	@GetMapping(value="/{pathFlag}/{registrationId}/oAuth2.do")
	public String oAuth2Login(@PathVariable String registrationId, @PathVariable String pathFlag, HttpSession httpSession){
		if(httpSession.getAttribute("oAuth2User") != null) {
			httpSession.removeAttribute("oAuth2User");
			httpSession.invalidate();	
		}
		
		OAuth2UserDTO oAuth2UserDTO = new OAuth2UserDTO();
		if(pathFlag.equals("login")) {
			oAuth2UserDTO.setPathFlag("login");
		} else {
	        oAuth2UserDTO.setPathFlag("join");
		}
		
        httpSession.setAttribute("oAuth2User", oAuth2UserDTO);
		return "redirect:/oauth2/authorization/" + registrationId;
	}
	
	/** OAuth2 기존 회원 여부 조회*/
	@GetMapping(value="/findByOAuth2User.do")
	public String findByOAuth2User(@SessionAttribute(value="oAuth2User", required = false) OAuth2UserDTO oAuth2UserDTO, Model model, HttpSession httpSession, Authentication authentication){
		UserDTO user = userService.findByOAuth2User(oAuth2UserDTO);
		if(user != null) {
			if(oAuth2UserDTO.getPathFlag().equals("login")) {
				model.addAttribute("userId", user.getUserId());
				model.addAttribute("OAuth2Login", "true");
				
		        return "user/loginForm";
			} else {
				model.addAttribute("existUser", user);
				return "user/joinForm";
			}
		} else {
			if(oAuth2UserDTO.getPathFlag().equals("login")) {
				httpSession.removeAttribute("oAuth2User");
				httpSession.invalidate();	
				
				model.addAttribute("result", "fail");
				return "user/loginForm";
			} else {
				model.addAttribute("oAuth2User", oAuth2UserDTO);
				return "user/joinForm";
			}
		}
	}

	/** ID 중복체크 */
	@GetMapping(value = "/findById.do")
	@ResponseBody
	public int findById(@RequestParam("userId") String userId) {
		int cnt = userService.findById(userId);
		return cnt;
	}
	
	/** 회원가입 - 필수 항목 UserDTO 저장 후 전달*/
	@ResponseBody
	@PostMapping(value = "/saveUserInfo.do")
	public HashMap<String, Object> saveUserInfo(@RequestBody UserDTO userDTO) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("userDTO", userDTO);
		
		return resultMap;
	}
	
	/** 회원가입 */
    @ResponseBody
    @PostMapping("/join.do")
    public HashMap<String, Object> join(@RequestBody String params) {
    	
    	UserDTO userDTO = new UserDTO();

    	int isInserted = 0;
    	ArrayList<UserDiseaseDTO> userDiseaseList = new ArrayList<>();
    	ArrayList<UserDrugDTO> userDrugList = new ArrayList<>();
    	
		try {
	    	JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(params);
			
	        Gson gson = new Gson();
	        userDTO = gson.fromJson(jsonObj.get("userDTO").toString(), UserDTO.class);
	        userDTO.setAuthority("ROLE_MEMBER");
	        
	    	if(userDTO.getRegistrationId() == null) {
	    		userDTO.setRegistrationId("main");
	    	}

	        JSONArray diseaseArr = (JSONArray) jsonObj.get("diseaseMap");
	        for (Object diseaseObj : diseaseArr) {
	        	UserDiseaseDTO userDiseaseDTO = new UserDiseaseDTO();
	        	userDiseaseDTO.setUserId(userDTO.getUserId());
	        	
		        int diseaseNo = Integer.parseInt(((JSONObject)diseaseObj).get("diseaseNo").toString());
	        	userDiseaseDTO.setDiseaseNo(diseaseNo);
	        	
	        	userDiseaseList.add(userDiseaseDTO);
	        }

	        JSONArray drugArr = (JSONArray) jsonObj.get("drugMap");
	        for (Object drugObj : drugArr) {
	        	UserDrugDTO userDrugDTO = new UserDrugDTO();
	        	userDrugDTO.setUserId(userDTO.getUserId());
	        	
		        int drugNo = Integer.parseInt(((JSONObject)drugObj).get("drugNo").toString());
		        userDrugDTO.setDrugNo(drugNo);
	        	
		        userDrugList.add(userDrugDTO);
	        }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		isInserted = userService.joinUser(userDTO, userDiseaseList, userDrugList);
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
    
    /*본인 인증 메세지*/
	@ResponseBody 
	@PostMapping("/PhoneCheck")
	public String PhoneCheck(@RequestParam(value="to") String to) throws CoolsmsException {
			
		return messageService.PhoneNumberCheck(to);
	}
    
}