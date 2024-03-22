package com.willy.malltest.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String SpecID;
    private String productName;
    private Integer productPrice;
    private String photoId;
}
