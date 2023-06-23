package com.kit.promokhapi.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePromotionInputDTO {
    @NotBlank
    private String promotionId;
}
