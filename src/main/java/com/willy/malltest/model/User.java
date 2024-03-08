package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long UserID;



    @OneToMany(mappedBy = "user")
    private Set<ThirdParty> orders = new HashSet<>();

    @Column(name = "UserName")
    private String username;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "RegisterDate")
    private Date RegisterDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "LastloginTime")
    private Date LastLoginTime;

    @Column(name = "UserAddress")
    private String UserAddress;
    @Column(name = "DeliverAddress")
    private String DeliverAddress;
    @Column(name = "Phone")
    private String Phone;
    @Column(name = "Authentication")
    private String Authentication;

}
