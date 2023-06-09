package com.kit.promokhapi.repository;

import com.kit.promokhapi.models.PromotionDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromotionDetailRepository extends MongoRepository<PromotionDetail, String> {
}
