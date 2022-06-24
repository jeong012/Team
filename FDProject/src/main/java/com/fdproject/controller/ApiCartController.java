package com.fdproject.controller;

import com.fdproject.domain.DrugsCartDTO;
import com.fdproject.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ApiCartController {
    private final DrugService drugService;

    @PostMapping("/add")
    public @ResponseBody boolean insert(@ModelAttribute DrugsCartDTO cartDTO) {

        return drugService.addMyDrug(cartDTO);
    }

    @PostMapping("/delete")
    public @ResponseBody boolean delete(@ModelAttribute DrugsCartDTO cartDTO) {

        return drugService.deleteMyDrug(cartDTO);
    }
}
