package com.fdproject.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.UserDTO;
import com.fdproject.domain.UserDiseaseDTO;
import com.fdproject.domain.UserDrugDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.DrugService;
import com.fdproject.service.RecipeService;
import com.fdproject.service.UserService;
import com.fdproject.util.UiUtils;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/mypage")
@RequiredArgsConstructor
public class MyPageController extends UiUtils {

//	private static final Logger log = LoggerFactory.getLogger(MyPageController.class);
	private final DiseaseService diseaseService;
	private final DrugService drugService;
	private final UserService userService;
	private final RecipeService recipeService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
//  @Autowired
//  private AuthenticationManager authenticationManager;
	
	//회원정보수정
	@GetMapping(value="/user.do")
	public String UserModify(Model model, Principal principal) {
		
		/** 마이페이지 - 사용자가 앓고 있는 지병 조회 */
		List<DiseaseDTO> userDiseaseList = diseaseService.getUserDiseaseList(principal.getName());
		model.addAttribute("userDiseaseList", userDiseaseList);

	    /** 마이페이지 - 사용자가 복용중인 약 조회 */
		List<DrugDTO> userDrugList = drugService.getUserDrugList(principal.getName());
		model.addAttribute("userDrugList", userDrugList);
		
		return "mypage/userModify";
	}
	
	/** 마이페이지 - 질병 리스트 조회 (사용자가 앓고 있는 지병 제외) */
    @ResponseBody
    @GetMapping("/getDiseaseList.do")
    public HashMap<String, Object> getMyPageDiseaseList(Model model, Principal principal) {
        List<DiseaseDTO> diseaseList = diseaseService.getMyPageDiseaseList(principal.getName());
		
        HashMap<String, Object> diseaseMap = new HashMap<String, Object>();
        diseaseMap.put("diseaseList", diseaseList);
        return diseaseMap;
    }

    /** 마이페이지 - 약 리스트 조회 (사용자가 복용중인 약 제외) */
    @ResponseBody
    @GetMapping("/getDrugList.do")
    public HashMap<String, Object> getMyPageDrugList(Model model, Principal principal) {
		List<DrugDTO> drugList = drugService.getMyPageDrugList(principal.getName());
        
        HashMap<String, Object> drugMap = new HashMap<String, Object>();
        drugMap.put("drugList", drugList);
        return drugMap;
    }
	
	//회원 정보 수정
	@ResponseBody
	@PostMapping(value="/modify.do")
	public String modifyUser(@RequestBody String params, Authentication authentication) {
		String result = "fail";
		
		UserDTO updateUserDTO = new UserDTO();
		UserDTO userDTO = (UserDTO)authentication.getPrincipal();

    	int isUpdated = 0;
    	ArrayList<UserDiseaseDTO> userDiseaseList = new ArrayList<>();
    	ArrayList<UserDrugDTO> userDrugList = new ArrayList<>();
    	
		try {
	    	JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(params);
			
	        Gson gson = new Gson();
	        updateUserDTO = gson.fromJson(jsonObj.get("userDTO").toString(), UserDTO.class);
	        if(!updateUserDTO.getPw().equals("") && userDTO.getRegistrationId().equals("main")) {
	        	String encPassword = passwordEncoder.encode(updateUserDTO.getPw());
	            userDTO.setPw(encPassword);
	        }
	        
	        if(!updateUserDTO.getName().equals(userDTO.getName())) {
	        	userDTO.setName(updateUserDTO.getName());
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
		
		isUpdated = userService.updateUser(userDTO, userDiseaseList, userDrugList);
		if(userDiseaseList.size() == 0 && userDrugList.size() == 0) {
			isUpdated = 1;
		}
   
        if (isUpdated > 0){
        	result = "success";
        }
        else {
        	result = "fail";
        }
		
		// 트랜젝션 종료, DB값 변경 완료
		// 세션값 변경
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUserId(), userDTO.getPw()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
		
	    return result;
	}
	
	/** 회원 정보 수정 - 비밀번호 확인 */
	@ResponseBody
	@PostMapping(value="/modify/checkPw.do")
	public String checkPw(@RequestParam("pw") String pw, Authentication authentication) {
		String result = "fail";
		
		UserDTO userDTO = (UserDTO)authentication.getPrincipal(); 
		if(passwordEncoder.matches(pw, userDTO.getPassword())){
			result = "success";
		}else {
			result = "fail";
		}
		
		return result;
	}
	
	//내질병관리
	@GetMapping(value="/disease.do")
	public String getDiseaseList(@ModelAttribute("params") DiseaseDTO params, @RequestParam(value = "id", required = false) String id, Model model, Authentication authentication){
		
		UserDTO userDTO = (UserDTO) authentication.getPrincipal();
		id = userDTO.getUserId();

        List<DiseaseDTO> diseaseList = diseaseService.getDiseaseList(id, params);
        model.addAttribute("diseaseList", diseaseList);
        model.addAttribute("id", id);
		 
		return "mypage/diseaseList";
	}
		
	
	//내 복용약 관리
	@GetMapping(value="/drug.do")
	public String getDrugList(@ModelAttribute("params") DrugDTO params, @RequestParam(value = "id", required = false) String id, Model model, Authentication authentication){
		
		UserDTO userDTO = (UserDTO) authentication.getPrincipal();
		id = userDTO.getUserId();
		
    	List<DrugDTO> userdrugList = drugService.getUserDrugList(id, params);
        model.addAttribute("userdrugList", userdrugList);
        model.addAttribute("id", id);
        
		return "mypage/drugList";
	}
	
	//찜한 약 리스트
	@GetMapping(value = "/myDrug.do")
	public String getMyDrugList(@ModelAttribute(value = "params") DrugDTO params, Model model, Principal principal) {

		String id = principal.getName();

		List<DrugDTO> drugList = drugService.getMyDrugList(id, params);
		model.addAttribute("drugList", drugList);
		return "mypage/myDrugList";
	}
	
	//찜한 레시피 리스트
	@GetMapping(value = "/myRecipe.do") 
	public String getMyRecipeList(@ModelAttribute(value = "params") RecipeDTO params, Model model, Principal principal) {
		//Recipe_List
		List<RecipeDTO> Recipe_List = recipeService.getMyRecipeList(params, principal);		
		model.addAttribute("Recipe_List", Recipe_List);
		return "mypage/myRecipeList";
	}

}
