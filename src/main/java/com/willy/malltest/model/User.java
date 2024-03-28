package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Data
@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "user")
    private List<ThirdParty> thirdParty = new ArrayList<>();

    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "register_date")
    private Date registerDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "user_address")
    private String userAddress;
    @Column(name = "deliver_address")
    private String deliverAddress;
    @Column(name = "phone")
    private String phone;
    @Column(name = "authentication")
    private Integer authentication;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Orders> orders = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<CustomerFeedback> customerFeedback = new HashSet<>();

    //test
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Track> Track;


}
