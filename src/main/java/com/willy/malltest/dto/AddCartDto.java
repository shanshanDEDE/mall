package com.willy.malltest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartDto {
    private Long userId;
    private String specId;
    private int quantity;
}
