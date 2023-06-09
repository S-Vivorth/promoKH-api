package com.kit.promokhapi.repository;

import com.kit.promokhapi.models.Promotion;
import com.kit.promokhapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PromotionRepository extends MongoRepository<Promotion, String> {
}
