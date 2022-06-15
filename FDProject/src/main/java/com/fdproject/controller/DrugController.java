package com.fdproject.controller;

import com.fdproject.domain.DrugDTO;
import com.fdproject.service.DrugService;
import com.fdproject.util.GrammerUtils;
import com.fdproject.util.UiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "drug")
@RequiredArgsConstructor
public class DrugController extends UiUtils {

    private final DrugService drugService;

    @GetMapping(value = "/list.do")
    public String getDrugList(@ModelAttribute("params") DrugDTO params, @RequestParam(value = "id", required = false) String id, Model model) {
        if (GrammerUtils.isStringEmpty(id) == true) {
            List<DrugDTO> drugList = drugService.getDrugList(id, params);
            model.addAttribute("drugList", drugList);
            return "drug/list";
        } else {
            List<DrugDTO> drugList = drugService.getDrugList(id, params);
            model.addAttribute("drugList", drugList);
        }

        return "drug/list";
    }

    @GetMapping(value = "/view")
    public String getDrug() {

        return "drug/view";
    }

    @GetMapping(value = "/find_pharmacy")
    public String getFindPharmacy() {

        return "drug/find_pharmacy";
    }

    @GetMapping(value="/find_store")
    public String getFindStore(){
        return "drug/find_store";
    }

}
