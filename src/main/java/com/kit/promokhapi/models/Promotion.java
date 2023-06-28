package com.kit.promokhapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Promotion {
    @Id
    private String id;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("category_id")
    private String categoryId;

    private String title;
    @JsonProperty("old_price")
    private Double oldPrice;
    @JsonProperty("discount_price")
    private Double discountPrice;
    @JsonProperty("discount_percentage")
    private Double discountPercentage;
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonProperty("end_date")
    private LocalDateTime endDate;
    @JsonProperty("feature_image_url")
    private String featureImageUrl;
    private String location;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    @JsonProperty("is_active")
    private boolean isActive;

    public Promotion(String userId, String categoryId, String title, Double oldPrice,
            Double discountPrice, Double discountPercentage, LocalDateTime startDate, LocalDateTime endDate,
            String featureImageUrl, String location, LocalDateTime createdDate, boolean isActive) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.oldPrice = oldPrice;
        this.discountPrice = discountPrice;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.featureImageUrl = featureImageUrl;
        this.location = location;
        this.createdDate = createdDate;
        this.isActive = isActive;
    }
}
