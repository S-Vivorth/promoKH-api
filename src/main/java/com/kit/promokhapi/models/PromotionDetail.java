package com.kit.promokhapi.models;

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

    private String promotionId;
    private String promotionDetail;
    private List<String> imageUrlList;
    private String contactNumber;
    private String facebookName;
    private String promotionUrl;
    private Float latitude;
    private Float longitude;
    private LocalDateTime createdDate;
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
