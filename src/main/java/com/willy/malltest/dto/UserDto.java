package com.willy.malltest.dto;


import lombok.Data;



import java.util.Date;

@Data
public class UserDto {

    private Long UserID;
    private String username;
    private String email;
    private String password;
    private Date RegisterDate;
    private Date LastLoginTime;
    private String UserAddress;
    private String DeliverAddress;
    private String Phone;
    private Integer Authentication;


}
