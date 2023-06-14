package com.kit.promokhapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
public class PromotionDetail {
    @Id
    private String id;
    @JsonProperty("promotionId")
    private String promotionId;
    @JsonProperty("promotion_detail")
    private String promotionDetail;
    @JsonProperty("image_url_list")
    private List<String> imageUrlList;
    @JsonProperty("contact_number")
    private String contactNumber;
    @JsonProperty("facebook_name")
    private String facebookName;
    @JsonProperty("promotion_url")
    private String promotionUrl;
    private Float latitude;
    private Float longitude;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    @JsonProperty("is_active")
    private boolean isActive;

    public PromotionDetail(String promotionId, String promotionDetail, List<String> imageUrlList,
            String contactNumber, String facebookName, String promotionUrl, Float latitude, Float longitude,
            LocalDateTime createdDate, boolean isActive) {
        this.promotionId = promotionId;
        this.promotionDetail = promotionDetail;
        this.imageUrlList = imageUrlList;
        this.contactNumber = contactNumber;
        this.facebookName = facebookName;
        this.promotionUrl = promotionUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdDate = createdDate;
        this.isActive = isActive;
    }
}
