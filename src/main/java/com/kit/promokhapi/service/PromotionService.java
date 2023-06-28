package com.kit.promokhapi.service;

import com.kit.promokhapi.helpers.StringHelper;
import com.kit.promokhapi.models.Promotion;
import com.kit.promokhapi.models.PromotionDetail;
import com.kit.promokhapi.repository.PromotionDetailRepository;
import com.kit.promokhapi.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
@Service
public class PromotionService {
    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    PromotionDetailRepository promotionDetailRepository;
    public void patch(Map<Object, Object> fields, String promotionId) {
        Optional<Promotion> promotionDb = promotionRepository.findById(promotionId);
        if (promotionDb.isPresent()) {
            fields.forEach((key, value) -> {
                System.out.println(StringHelper.toCamelCase((String)key));
                Field field = ReflectionUtils.findField(Promotion.class, StringHelper.toCamelCase((String)key));
                field.setAccessible(true);
                ReflectionUtils.setField(field, promotionDb.get(), value);
            });

            promotionRepository.save(promotionDb.get());
        }

    }

    public Promotion deleteById(String id) {
        Optional<Promotion> promotion = promotionRepository.findById(id);
        if (promotion.isPresent()) {
            // remove promotion
            promotionRepository.deleteById(id);

            // remove promotion detail
            Query query = new Query();
            query.addCriteria(Criteria.where(StringHelper.toCamelCase("promotion_id")).is(id));
            mongoTemplate.findAndRemove(query, PromotionDetail.class);

            return promotion.get();
        }
        else {
            throw new RuntimeException();
        }
    }

    public PromotionDetail findByPromotionId(String promotionId) {
        Optional<Promotion> promotion = promotionRepository.findById(promotionId);
        if (promotion.isPresent()) {
            Query query = new Query();
            query.addCriteria(Criteria.where(StringHelper.toCamelCase("promotion_id")).is(promotionId));
            PromotionDetail promotionDetail = mongoTemplate.findOne(query, PromotionDetail.class);
            return promotionDetail;
        }
        else {
            throw new RuntimeException();
        }
    }
}
