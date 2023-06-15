package com.kit.promokhapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AddPromotionDTO {
    @JsonProperty("promotion_id")
    private String promotionId;
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
    @JsonProperty("feature_image")
    private String featureImageUrl;
    @JsonProperty("location")
    private String location;
}

