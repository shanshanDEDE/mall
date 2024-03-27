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
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "user")
    private Set<ThirdParty> thirdParty = new HashSet<>();

    @Column
    private String userName;
    @Column
    private String email;
    @Column
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column
    private Date registerDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column
    private Date lastLoginTime;

    @Column
    private String userAddress;
    @Column
    private String deliverAddress;
    @Column
    private String phone;
    @Column
    private Integer authentication;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
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
