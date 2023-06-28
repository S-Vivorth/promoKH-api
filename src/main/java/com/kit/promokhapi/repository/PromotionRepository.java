package com.kit.promokhapi.repository;

import com.kit.promokhapi.models.Promotion;
import com.kit.promokhapi.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends MongoRepository<Promotion, String> {

    Page<Promotion> findByCategoryId(String categoryId, Pageable pageable);




}
