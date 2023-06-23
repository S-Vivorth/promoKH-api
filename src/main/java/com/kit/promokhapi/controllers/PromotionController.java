package com.kit.promokhapi.controllers;

import com.kit.promokhapi.dto.AddPromotionDTO;
import com.kit.promokhapi.dto.ResponseDTO;

import com.kit.promokhapi.jwt.JwtHelper;
import com.kit.promokhapi.models.PostPromoReqModel;
import com.kit.promokhapi.models.Promotion;
import com.kit.promokhapi.models.PromotionDetail;
import com.kit.promokhapi.models.User;
import com.kit.promokhapi.repository.PromotionDetailRepository;
import com.kit.promokhapi.repository.PromotionRepository;


import com.kit.promokhapi.repository.UserRepository;
import com.kit.promokhapi.service.PromotionService;
import com.kit.promokhapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/promo_kh")
public class PromotionController {

    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    PromotionDetailRepository promotionDetailRepository;

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    PromotionService promotionService;
    @PostMapping("/promotion/add")
    public ResponseEntity<?> post(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                  @Valid @RequestBody PostPromoReqModel reqModel) {
        String token = authorization.replace("Bearer ", "");
        boolean isAuth = jwtHelper.validateAccessToken(token);
        if (isAuth) {
            String userId = jwtHelper.getUserIdFromAccessToken(token);
            Promotion promotion = new Promotion(
                    userId,
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
                    promotion.getId(),
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
        else {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", null));
        }

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
    @PatchMapping("/user/posted_promotion/update")
    public ResponseEntity<?> update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                    @RequestBody Map<Object, Object> payload, @RequestParam("promotion_id") String promotionId) {

        boolean isAuth = jwtHelper.validateAccessToken(authorization);
        if (isAuth) {
            promotionService.patch(payload, promotionId);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "success", null));
        }
        else {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", null));
        }
    }


    @PostMapping("/user/posted_promotion/delete")
    public  ResponseEntity<?> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                     @RequestBody  Map<String, Object> payload) {
        boolean isAuth = jwtHelper.validateAccessToken(authorization);
        if (isAuth) {
            try {
                Promotion deletePromotion = promotionService.deleteById((String) payload.get("promotion_id"));
                return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "Promotion has been deleted successfully.", deletePromotion));
            }
            catch (RuntimeException exc) {
                return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Promotion not found", null));
            }
        }
        else {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "unauthorized", null));
        }
    }

    @GetMapping("/promotion_detail/get")
    public ResponseEntity<?> getPromotionDetail(@RequestParam String promotion_id) {

        try {
            PromotionDetail promotionDetail = promotionService.findByPromotionId(promotion_id);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "Promotion detail has been fetched successfully.", promotionDetail));
        }
        catch (RuntimeException exc) {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Promotion detail not found", null));
        }


    }
    @Autowired
    MongoTemplate mongoTemplate;
    @GetMapping("/posted_promotion/get")
    public ResponseEntity<?> getPostPromotion(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        String token = authorization.replace("Bearer", "");
        boolean isAuth = jwtHelper.validateAccessToken(token);
        if (isAuth) {
            String userId = jwtHelper.getUserIdFromAccessToken(token);
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(userId));
            List<Promotion> promotionList = mongoTemplate.find(query, Promotion.class);
            ResponseDTO<List<Promotion>> responseDTO = new ResponseDTO<>(
                    HttpStatus.OK.value(),
                    "success",
                    promotionList
            );
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", null));
        }

    }
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/saved_promotion/add")
    public ResponseEntity<?> savePromotion( @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                           @Valid @RequestBody String savedPromotionId) {
        String token = authorization.replace("Bearer", "");
        String userId = jwtHelper.getUserIdFromAccessToken(token);
        boolean isAuth = jwtHelper.validateAccessToken(token);
        if (isAuth) {
            User user = userService.findById(userId);
            String promotion_id = savedPromotionId.replaceAll("[^a-zA-Z0-9]", "")
                    .replaceFirst("promotionid", "");
            List<String> savedPromotions = user.getSavedPromotionIdList();
            for (String item: savedPromotions){
                if (promotion_id.equals(item)){
                    ResponseDTO<List<String>> responseDTO = new ResponseDTO<>(
                            HttpStatus.OK.value(),
                            "Already Saved",
                            savedPromotions
                    );
                    return ResponseEntity.ok(responseDTO);

            }}
            user.getSavedPromotionIdList().add(promotion_id);
            userRepository.save(user);
            ResponseDTO<List<String>> responseDTO = new ResponseDTO<>(
                    HttpStatus.OK.value(),
                    "success",
                    savedPromotions
            );
            return ResponseEntity.ok(responseDTO);
    } else {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", null));
        }
    }
    @GetMapping("/saved_promotion/get")
    public ResponseEntity<?> getSavedPromotion(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){

        String token = authorization.replace("Bearer", "");
        String userId = jwtHelper.getUserIdFromAccessToken(token);
        User user = userService.findById(userId);
        boolean isAuth = jwtHelper.validateAccessToken(token);
        if (isAuth) {
            List<String> savedPromotion = user.getSavedPromotionIdList();
            List<Optional<Promotion>> savePromotion = new ArrayList<>();
            for (String item: savedPromotion){
                Optional<Promotion> promotion = promotionRepository.findById(item);
                savePromotion.add(promotion);
            }
            ResponseDTO<List<Optional<Promotion>>> responseDTO = new ResponseDTO<>(
                    HttpStatus.OK.value(),
                    "success",
                    savePromotion
            );
            return ResponseEntity.ok(responseDTO);
        }else {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", null));
        }
    }


}




