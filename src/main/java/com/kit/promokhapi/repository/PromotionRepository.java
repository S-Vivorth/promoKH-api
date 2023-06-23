package com.kit.promokhapi.repository;

import com.kit.promokhapi.models.Promotion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromotionRepository extends MongoRepository<Promotion, String> {

    Page<Promotion> findByCategoryId(String categoryId, Pageable pageable);

}
