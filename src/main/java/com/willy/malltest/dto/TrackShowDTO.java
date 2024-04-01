package com.willy.malltest.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackShowDTO {
    private Integer trackID;
    private Long userID;
    private String specID;
    private Integer productPrice;
    private byte[] photoFile;
    private String productName;
}
