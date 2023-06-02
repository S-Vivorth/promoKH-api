package com.kit.promokhapi.service;

import org.springframework.stereotype.Service;

import com.kit.promokhapi.models.Category;
import com.kit.promokhapi.repository.CategoryRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CategorySerivce {
    
    @Autowired
    CategoryRepository db;

    // get all categories with jpa
    public List<Category> getAllCategories() {
        return db.findAll();
    }

  
    
}
