package com.kit.promokhapi.controllers;

import com.kit.promokhapi.dto.PostedPromotionDTO;
import com.kit.promokhapi.models.Promotion;
import com.kit.promokhapi.repository.PostedPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/promo_kh")
public class PostedPromotionController {
    @Autowired
    private PostedPromotionRepository postedPromotionRepository;
    @GetMapping("/posted_promotion")
    public PostedPromotionDTO<List<Promotion>> getAllPostedPromotion() {
        List<Promotion> listPromotion = postedPromotionRepository.findAll();
        return new PostedPromotionDTO<>(HttpStatus.OK.value(), "Success", listPromotion);
    }
}
