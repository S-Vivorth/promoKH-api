package com.kit.promokhapi.controllers;

import com.kit.promokhapi.dto.AddPromotionDTO;
import com.kit.promokhapi.dto.ResponseDTO;
import com.kit.promokhapi.models.PostPromoReqModel;
import com.kit.promokhapi.models.Promotion;
import com.kit.promokhapi.models.PromotionDetail;
import com.kit.promokhapi.repository.PromotionDetailRepository;
import com.kit.promokhapi.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/promo_kh/promotion")
public class PromotionController {

    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    PromotionDetailRepository promotionDetailRepository;
    @PostMapping("/add")
    public ResponseEntity<?> post(@Valid @RequestBody PostPromoReqModel reqModel) {

        Promotion promotion = new Promotion(
                reqModel.getCategoryId(),
                reqModel.getTitle(),
                reqModel.getOldPrice(),
                reqModel.getDiscountPrice(),
                reqModel.getDiscountPercentage(),
                reqModel.getStartDate(),
                reqModel.getEndDate(),
                reqModel.getFeatureImageUrl(),
                reqModel.getLocation(),
                LocalDateTime.now(),
                true
        );
        promotionRepository.save(promotion);

        PromotionDetail promotionDetail = new PromotionDetail(
                promotion.getId(),
                reqModel.getPromotionDetail(),
                reqModel.getImageUrlList(),
                reqModel.getContactNumber(),
                reqModel.getFacebookName(),
                reqModel.getPromotionUrl(),
                reqModel.getLongtitude(),
                reqModel.getLatitude(),
                LocalDateTime.now(),
                true
        );
        promotionDetailRepository.save(promotionDetail);

        AddPromotionDTO addPromotionDTO = new AddPromotionDTO(
                promotionDetail.getId(),
                reqModel.getCategoryId(),
                reqModel.getTitle(),
                reqModel.getOldPrice(),
                reqModel.getDiscountPrice(),
                reqModel.getDiscountPercentage(),
                reqModel.getStartDate(),
                reqModel.getEndDate(),
                reqModel.getFeatureImageUrl(),
                reqModel.getLocation()
        );

        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "success", addPromotionDTO));
    }

}
