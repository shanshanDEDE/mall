package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "Users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userID;

    @OneToMany(mappedBy = "user")
    private Set<ThirdParty> thirdParty = new HashSet<>();

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
    private Integer Authentication;

    @OneToMany(mappedBy = "userID", cascade = CascadeType.ALL)
    private Set<Orders> orders = new HashSet<>();


    //test
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Track> Track;

    public void add(Orders order) {

        if (order != null) {

            if (orders == null) {
                orders = new HashSet<>();
            }

            orders.add(order);
            order.setUserID(this);
        }
    }
}
