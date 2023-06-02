package com.kit.promokhapi.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kit.promokhapi.models.Category;



@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    
}
