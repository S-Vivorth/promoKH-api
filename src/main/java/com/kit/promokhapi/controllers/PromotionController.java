package com.kit.promokhapi.controllers;

import com.kit.promokhapi.dto.AddPromotionDTO;
import com.kit.promokhapi.dto.ResponseDTO;
import com.kit.promokhapi.dto.SavePromotionInputDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/promotion/add")
    public ResponseEntity<?> post(@Valid @RequestBody PostPromoReqModel reqModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Promotion promotion = new Promotion(
                user.getId(),
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
                true);
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
                true);
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
                reqModel.getLocation());

        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "success", addPromotionDTO));

    }

    @GetMapping("/promotion/get")
    public ResponseEntity<?> getByCategory(@RequestParam(required = false) String category_Id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {

        if (category_Id == null || category_Id.isEmpty()) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Promotion> promotionPage = promotionRepository.findAll(pageable);
            List<Promotion> promotionList = promotionPage.getContent();
            ResponseDTO<List<Promotion>> responseDTO = new ResponseDTO<>(
                    HttpStatus.OK.value(),
                    "success",
                    promotionList);
            return ResponseEntity.ok(responseDTO);
        }


@GetMapping("/promotion/get")
public ResponseEntity<?> getByCategory(@RequestParam String category_id,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "25") int size) {

   if (category_id == null || category_id.isEmpty()) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Promotion> promotionPage = promotionRepository.findByCategoryId(category_Id, pageable);

        List<Promotion> promotionList = promotionPage.getContent();

        ResponseDTO<List<Promotion>> responseDTO = new ResponseDTO<>(
                HttpStatus.OK.value(),
                "success",
                promotionList);

        return ResponseEntity.ok(responseDTO);
    }                                 
    
    Pageable pageable = PageRequest.of(page, size);
    Page<Promotion> promotionPage = promotionRepository.findByCategoryId(category_id, pageable);
    
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

    @GetMapping("/user/posted_promotion/get")
    public ResponseEntity<?> getPostPromotion() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(user.getId()));
        List<Promotion> promotionList = mongoTemplate.find(query, Promotion.class);
        ResponseDTO<List<Promotion>> responseDTO = new ResponseDTO<>(
                HttpStatus.OK.value(),
                "success",
                promotionList);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/user/posted_promotion/update")
    public ResponseEntity<?> update(@RequestBody Map<Object, Object> payload, @RequestParam String promotionId) {
        promotionService.patch(payload, promotionId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "success", null));
    }

    @DeleteMapping("/user/posted_promotion/delete")
    public ResponseEntity<?> delete(@RequestParam String promotionId) {
        try {
            Promotion deletePromotion = promotionService.deleteById(promotionId);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(),
                    "Promotion has been deleted successfully.", deletePromotion));
        } catch (RuntimeException exc) {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Promotion not found", null));
        }
    }

    @GetMapping("/promotion_detail/get")
    public ResponseEntity<?> getPromotionDetail(@RequestParam String promotionId) {
        try {
            PromotionDetail promotionDetail = promotionService.findByPromotionId(promotionId);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(),
                    "Promotion detail has been fetched successfully.", promotionDetail));
        } catch (RuntimeException exc) {
            return ResponseEntity
                    .ok(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Promotion detail not found", null));
        }
    }

    @PostMapping("/saved_promotion/add")
    public ResponseEntity<?> savePromotion(@Valid @RequestBody SavePromotionInputDTO payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String promotion_id = payload.getPromotionId().replaceAll("[^a-zA-Z0-9]", "")
                .replaceFirst("promotionid", "");
        try {
            Optional<Promotion> savePromotion = promotionRepository.findById(promotion_id);
            List<String> savedPromotions = user.getSavedPromotionIdList();
            for (String item : savedPromotions) {
                if (promotion_id.equals(item)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT.value())
                            .body(new ResponseDTO<>(HttpStatus.CONFLICT.value(), "Already Saved", null));
                }
            }
            user.getSavedPromotionIdList().add(promotion_id);
            userRepository.save(user);
            ResponseDTO<Optional<Promotion>> responseDTO = new ResponseDTO<Optional<Promotion>>(
            for (String item: savedPromotions){
                if (!promotion_id.equals(item)){
                    user.getSavedPromotionIdList().add(promotion_id);
                    userRepository.save(user);
                    ResponseDTO<List<String>> responseDTO = new ResponseDTO<>(
                            HttpStatus.OK.value(),
                            "success",
                            savedPromotions
                    );
                    return ResponseEntity.ok(responseDTO);
                }else {
                    ResponseDTO<List<String>> responseDTO = new ResponseDTO<>(
                            HttpStatus.OK.value(),
                            "Already Saved",
                            savedPromotions
                    );
                    return ResponseEntity.ok(responseDTO);
                }
            }
        }
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", null));
    }
    @GetMapping("/saved_promotion/get")
    public ResponseEntity<?> getSavedPromotion(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
                                               ){

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
                    savePromotion);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException exc) {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Promotion not found", null));
        }
    }

    @GetMapping("/saved_promotion/get")
    public ResponseEntity<?> getSavedPromotion() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<String> savedPromotion = user.getSavedPromotionIdList();
        List<Optional<Promotion>> savePromotion = new ArrayList<>();
        for (String item : savedPromotion) {
            Optional<Promotion> promotion = promotionRepository.findById(item);
            savePromotion.add(promotion);
    @GetMapping("/promotion/search")
    public ResponseEntity<?> searchPromotion(@RequestParam String keyword,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "25") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Promotion> promotionPage = promotionService.search(keyword, pageable);
        List<Promotion> promotionList = promotionPage.getContent();
        ResponseDTO<List<Promotion>> responseDTO = new ResponseDTO<>(
                HttpStatus.OK.value(),
                "success",
                promotionList
        );
        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping("/saved_promotion/delete")
    public ResponseEntity<?> deleteSavedPromotion(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                                  @RequestBody Map<Object, Object> payload) {
        String token = authorization.replace("Bearer ", "");
        boolean isAuth = jwtHelper.validateAccessToken(token);

        if (isAuth) {
            String userId = jwtHelper.getUserIdFromAccessToken(token);
            User user = userService.findById(userId);
            List<String> savedPromotionIdList = user.getSavedPromotionIdList();
            if (payload.get("promotion_id") != null) {
                String promotionId = (String)payload.get("promotion_id");
                if (savedPromotionIdList.contains(promotionId)) {
                    savedPromotionIdList.remove(promotionId);
                    user.setSavedPromotionIdList(savedPromotionIdList);
                    userRepository.save(user);
                    return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "Saved promotion has been deleted successfully.", null));
                }
                else {
                    return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Promotion id not found.", null));
                }

            }
            else {
                return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.EXPECTATION_FAILED.value(), "promotion_id field not found", null));
            }
        }
        else {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", null));
        }
        ResponseDTO<List<Optional<Promotion>>> responseDTO = new ResponseDTO<>(
                HttpStatus.OK.value(),
                "success",
                savePromotion);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/saved_promotion/delete")
    public ResponseEntity<?> deleteSavedPromotion(@RequestParam String promotionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<String> savedPromotionIdList = user.getSavedPromotionIdList();
        if (savedPromotionIdList.contains(promotionId)) {
            savedPromotionIdList.remove(promotionId);
            user.setSavedPromotionIdList(savedPromotionIdList);
            userRepository.save(user);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(),
                    "Saved promotion has been deleted successfully.", null));
        } else {
            return ResponseEntity
                    .ok(new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Promotion id not found.", null));
        }
    }
}
