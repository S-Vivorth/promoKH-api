package com.kit.promokhapi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Category {
    @Id
    private String id;

    private String name;
    private String iconUrl;
    private LocalDateTime createdDate;
    private boolean isActive;

    public Category(String name, String iconUrl, LocalDateTime createdDate, boolean isActive) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.createdDate = createdDate;
        this.isActive = isActive;
    }
}
