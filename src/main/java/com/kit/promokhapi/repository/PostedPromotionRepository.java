package com.kit.promokhapi.repository;

import com.kit.promokhapi.models.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostedPromotionRepository extends MongoRepository<Promotion, String> {
}
