package com.willy.malltest.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private String SpecId;
    private Integer productPhotoId;
    private String productName;
    private Integer productPrice;
    private Integer quantity;
}
