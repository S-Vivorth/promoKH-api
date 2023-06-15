package com.kit.promokhapi.controllers;

import com.kit.promokhapi.dto.AddPromotionDTO;
import com.kit.promokhapi.dto.ResponseDTO;

import com.kit.promokhapi.models.PostPromoReqModel;
import com.kit.promokhapi.models.Promotion;
import com.kit.promokhapi.models.PromotionDetail;
import com.kit.promokhapi.repository.PromotionDetailRepository;
import com.kit.promokhapi.repository.PromotionRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/promo_kh")
public class PromotionController {

    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    PromotionDetailRepository promotionDetailRepository;
  

    @PostMapping("/promotion/add")
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



@GetMapping("/promotion/get")
public ResponseEntity<?> getByCategory(@RequestParam String category_Id,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "25") int size) {

   if (category_Id == null || category_Id.isEmpty()) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Promotion> promotionPage = promotionRepository.findAll(pageable);
        List<Promotion> promotionList = promotionPage.getContent();    
        ResponseDTO<List<Promotion>> responseDTO = new ResponseDTO<>(
                HttpStatus.OK.value(),
                "success",
                promotionList
        );
        return ResponseEntity.ok(responseDTO);
    }                                 
    
    Pageable pageable = PageRequest.of(page, size);
    Page<Promotion> promotionPage = promotionRepository.findByCategoryId(category_Id, pageable);
    
    List<Promotion> promotionList = promotionPage.getContent();
    
    ResponseDTO<List<Promotion>> responseDTO = new ResponseDTO<>(
            HttpStatus.OK.value(),
            "success",
            promotionList
    );
    
    return ResponseEntity.ok(responseDTO);
}
@Autowired
MongoTemplate mongoTemplate;
@GetMapping("/posted_promotion/get")
    public ResponseEntity<?> getPostPromotion(@RequestParam String user_id){
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(user_id));
    List<Promotion> promotionList = mongoTemplate.find(query, Promotion.class);
    ResponseDTO<List<Promotion>> responseDTO = new ResponseDTO<>(
            HttpStatus.OK.value(),
            "success",
            promotionList
    );
    return ResponseEntity.ok(responseDTO);
}

}

