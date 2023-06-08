package com.kit.promokhapi.service;

import com.kit.promokhapi.models.Promotion;
import com.kit.promokhapi.repository.PostedPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class PostedPromotionService {
    @Autowired
    PostedPromotionRepository db;

    public List<Promotion> getAllPostedPromotion(){
        return db.findAll();
    }

}
