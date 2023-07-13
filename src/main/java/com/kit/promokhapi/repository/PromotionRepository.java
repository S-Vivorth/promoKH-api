package com.kit.promokhapi.repository;

import com.kit.promokhapi.models.Promotion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PromotionRepository extends MongoRepository<Promotion, String> {

    Page<Promotion> findByCategoryId(String categoryId, Pageable pageable);

    @Query("{'$or': [{'title': {$regex: ?0, $options: 'i'}}, {'location': {$regex: ?0, $options: 'i'}}]}")
    Page<Promotion> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

}
