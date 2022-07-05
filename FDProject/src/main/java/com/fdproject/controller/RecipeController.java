package com.fdproject.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fdproject.domain.DiseaseDTO;
import com.fdproject.domain.RecipeDTO;
import com.fdproject.service.DiseaseService;
import com.fdproject.service.RecipeService;
   

@Controller
@RequestMapping("/recipe")
public class RecipeController {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired 
	private RecipeService recipeService;
	@Autowired 
	private DiseaseService diseaseService;
	
	@GetMapping(value="/list")
	public String getRecipeList(@ModelAttribute("params") RecipeDTO params,
			Model model){					 				
		// 레시피 리스트 뽑아오기
		
		
		List<RecipeDTO> Recipe_List = recipeService.getRecipeList(params);		
		model.addAttribute("Recipe_List", Recipe_List);
		System.out.println("Recipe_List:" + Recipe_List);
		//disease_list 상위 5개 가져오는 객체
		List<DiseaseDTO> Disease_List_Five = diseaseService.getDiseaseListFive();
		model.addAttribute("Disease_List_Five", Disease_List_Five);		
		//System.out.println("Disease_List_Five:" + Disease_List_Five);
						
		return "recipe/list";
	}

	@GetMapping(value="/view") 
	public String getRecipe(@RequestParam(value = "Recipe_No", required = false) String Recipe_No, Model model){
				
		//recipe_info
		RecipeDTO Recipe_info = recipeService.getRecipeInfo(Recipe_No);
		model.addAttribute("Recipe_info", Recipe_info);
		System.out.println("Recipe_info" + Recipe_info);
		//foodIngredients split해서 배열에 차곡차곡 넣음 
		String recipe_ingredients = Recipe_info.getFoodIngredients();		
		String[] ri_split = recipe_ingredients.split("\n");		
		ArrayList<String> AL_ri_split = new ArrayList<>();				
		for(int i=0; i<ri_split.length; i++){
			AL_ri_split.add(ri_split[i]);			
		}				
		model.addAttribute("AL_ri_split", AL_ri_split);
		
		//step '][' 기준으로 자르기		
		//recipe_step 가져오기
		String recipe_step = Recipe_info.getStep();
		//model로 넘길 arrayList 생성		
		ArrayList<String> AL_rs_split = new ArrayList<>();
		//System.out.println(recipe_step); 
		String temp = recipe_step;
		boolean run = true;
		while(run) {
			String data = "";
			
			if (temp.indexOf("\n[") != -1) {
				int startIndex = temp.indexOf("] ") + "] ".length();
				int endIndex = temp.indexOf("\n[");
				
				data = temp.substring(startIndex, endIndex);
				temp = temp.substring(endIndex+1);	
			} else {
				data = temp.substring(temp.indexOf("] ") + "] ".length());
				run = false;
			}
			
			System.out.println(data);
			//data arraylist에 넣기
			AL_rs_split.add(data);
		}		
		model.addAttribute("AL_rs_split", AL_rs_split);
		
		
		//조회수 관련 - 구현 완료
		recipeService.uphit(Recipe_No);
		
		
		return "recipe/view";
	}

	@GetMapping(value="/writeForm")
	public String getRecipeForm(){
		return "recipe/writeForm";
	}
	
	@ResponseBody
	@PostMapping(value="/add")
	public String addRecipe(@ModelAttribute RecipeDTO params){
		System.out.println("recipedto:" + params);
		recipeService.uploadRecipe(params);
		return "recipe/list"; 
	}
	
    @PostMapping(value="/upload")
    public void uploadAjaxPost(MultipartFile[] uploadFile) {
    	String uploadFolder = "C:\\upload";
    	for(MultipartFile multipartFile : uploadFile) {
    		System.out.println("Upload File Name:" + multipartFile.getOriginalFilename());
    		System.out.println("Upload File Size:" + multipartFile.getSize());
    		
    		String uploadFileName = multipartFile.getOriginalFilename();
    		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
    		
    		System.out.println("only file name:" + uploadFileName);
    		
    		File saveFile = new File(uploadFolder, uploadFileName);
    		try {
    			multipartFile.transferTo(saveFile);
    		}catch(Exception e) {
    			System.out.println(e.getMessage());
    		}
    		
    		}    	
    	 
    }
}
