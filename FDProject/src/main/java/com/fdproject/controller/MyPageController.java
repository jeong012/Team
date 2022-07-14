package com.fdproject.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.DrugDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.domain.UserDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.DrugService;
import com.fdproject.service.RecipeService;
import com.fdproject.service.UserService;
import com.fdproject.util.UiUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="mypage")
@RequiredArgsConstructor
public class MyPageController extends UiUtils {

//	private static final Logger log = LoggerFactory.getLogger(MyPageController.class);
	private final DiseaseService diseaseService;
	private final DrugService drugService;
	private final UserService userService;
	private final RecipeService recipeService;
	
//  @Autowired
//  private AuthenticationManager authenticationManager;
	
	@GetMapping(value="/user.do")
	public String UserModify(@ModelAttribute("params") UserDTO params, @RequestParam(value = "id", required = false) String id, Model model) {
		
		return "mypage/usermodify";
	}
	
	//회원 정보 수정
	@ResponseBody
	@PutMapping(value="/modify.do")
	public String UserModify(@RequestBody UserDTO userDTO) {
		System.out.println(userDTO);
		userService.updateUser(userDTO);
		// 트랜젝션 종료, DB값 변경 완료
		// 세션값 변경
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUserId(), userDTO.getPw()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
		
	    return "success";
	}
	
	
//	//내 질병 관리
//	
//	@GetMapping(value="/disease.do")
//	public String getDiseaseList(@ModelAttribute("params") DiseaseDTO params, @RequestParam(value = "id", required = false) String id, Model model){
//		
//        List<DiseaseDTO> diseaseList = diseaseService.getDiseaseList(id, params);
//        model.addAttribute("diseaseList", diseaseList);
//        model.addAttribute("id", id);
//	        
//		 
//		return "mypage/diseaselist";
//	}
	
		
		@GetMapping(value="/disease.do")
		public String getDiseaseList(@ModelAttribute("params") DiseaseDTO params, @RequestParam(value = "id", required = false) String id, Model model, HttpSession httpSession){
			
	        List<DiseaseDTO> diseaseList = diseaseService.getDiseaseList(id, params);
	        model.addAttribute("diseaseList", diseaseList);
	        model.addAttribute("id", id);
		        
			 
			return "mypage/diseaselist";
		}
		
	
	//내 복용약 관리
	@GetMapping(value="/drug.do")
	public String getDrugList(@ModelAttribute("params") DrugDTO params, @RequestParam(value = "id", required = false) String id, Model model){
		
    	List<DrugDTO> userdrugList = drugService.getUserDrugList(id, params);
        model.addAttribute("userdrugList", userdrugList);
        model.addAttribute("id", id);
        
		return "mypage/druglist";
	}
	
	//찜한 약 리스트
	@GetMapping(value = "/mydrug.do")
	public String getMyDrugList(@ModelAttribute(value = "params") DrugDTO params, Model model) {		
		
		List<DrugDTO> drugList = drugService.getMyDrugList(params);		
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
