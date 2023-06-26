package com.kit.promokhapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class PostPromoReqModel {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("category_id")
    private String categoryId;
    @DBRef
    private List<User> savedBy;

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
    @JsonProperty("image_url_list")
    private List<String> imageUrlList;
    private String location;
    @JsonProperty("promotion_detail")
    private String promotionDetail;
    @JsonProperty("contact_number")
    private String contactNumber;
    @JsonProperty("facebook_name")
    private String facebookName;
    @JsonProperty("promotion_url")
    private String promotionUrl;

    private Float latitude;

    private Float longtitude;


    public PostPromoReqModel(String userId, String categoryId, String title, Double oldPrice,
                             Double discountPrice, Double discountPercentage, LocalDateTime startDate, LocalDateTime endDate,
                             String featureImageUrl,List<String> imageUrlList, String location,String promotionDetail,
                             String contactNumber,String facebookName, String promotionUrl, Float latitude,
                             Float longtitude
                             ) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.savedBy = new ArrayList<>();
        this.title = title;
        this.oldPrice = oldPrice;
        this.discountPrice = discountPrice;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.featureImageUrl = featureImageUrl;
        this.imageUrlList = imageUrlList;
        this.location = location;
        this.promotionDetail = promotionDetail;
        this.contactNumber = contactNumber;
        this.facebookName = facebookName;
        this.promotionUrl = promotionUrl;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }


}
