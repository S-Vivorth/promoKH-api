package com.kit.promokhapi.models;

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

    private String categoryId;

    private String title;
    private Double oldPrice;
    private Double discountPrice;
    private Double discountPercentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String featureImageUrl;
    private String location;
    private LocalDateTime createdDate;
    private boolean isActive;

    public Promotion( String categoryId, String title, Double oldPrice,
            Double discountPrice, Double discountPercentage, LocalDateTime startDate, LocalDateTime endDate,
            String featureImageUrl, String location, LocalDateTime createdDate, boolean isActive) {
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
