package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Orders {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;  //PRIMARY KEY identity(1,1),

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  //foreign key,

    @Column
    private Date orderDate;
    @Column
    private String paymentMethod;
    @Column
    private String orderStatus;
    @Column
    private Date deliverDate;
    @Column
    private Date pickupDate;
    @Column
    private String deliverAddress;
    @Column
    private String recipientName;
    @Column
    private String recipientPhone;
    @Column
    private Date paymentTime;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrdersDetail> ordersDetails = new HashSet<OrdersDetail>();

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<CustomerFeedback> customerFeedbacks = new HashSet<CustomerFeedback>();


}
