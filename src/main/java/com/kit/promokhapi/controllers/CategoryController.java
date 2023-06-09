package com.kit.promokhapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kit.promokhapi.models.Category;
import com.kit.promokhapi.service.CategorySerivce;

@CrossOrigin("*")
@RestController
public class CategoryController {

    @Autowired
    CategorySerivce service;
    
    // get all categories
    @GetMapping("promo_kh/category")
    public List<Category> getAllCategories() {
        return service.getAllCategories();
    }
    
}
