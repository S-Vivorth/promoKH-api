package com.kit.promokhapi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Document
@Data
public class RefreshToken {
    @Id
    String id;
    @DBRef(lazy = true)
    private User owner;
}
