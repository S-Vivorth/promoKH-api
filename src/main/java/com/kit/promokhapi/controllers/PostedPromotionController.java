package com.kit.promokhapi.controllers;


import com.kit.promokhapi.dto.ResponseDTO;

import com.kit.promokhapi.jwt.JwtHelper;
import com.kit.promokhapi.models.Promotion;
import com.kit.promokhapi.repository.RefreshTokenRepository;
import com.kit.promokhapi.service.PostedPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/promo_kh")
public class PostedPromotionController {
    @Autowired
    private PostedPromotionService postedPromotionService;
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/posted_promotion")
    public ResponseEntity<ResponseDTO<List<Promotion>>> getAllPostedPromotion(HttpServletRequest httpServletRequest, @RequestHeader("api_token") String apiToken) {
//        String authorizationHeader = httpServletRequest.getHeader("api_token");
        if (jwtHelper.validateRefreshToken(apiToken)){
            List<Promotion> listPromotion = postedPromotionService.getAllPostedPromotion();
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "Success", listPromotion));
        } else {
            ResponseDTO<List<Promotion>> responseDTO = new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), "Invalid token", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(responseDTO);
        }
    }
}
