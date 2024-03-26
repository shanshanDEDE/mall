package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Orders")
public class Orders {

    @Id
    @Column(name = "OrderID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;  //PRIMARY KEY identity(1,1),

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User userID;  //foreign key,
    //↑為什麼userID的型別是User?

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "OrderDate")
    private Date orderDate;

    @Column(name = "PaymentMethod")
    private String paymentMethod;

    @Column(name = "OrderStatus")
    private String orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "DeliverDate")
    private Date deliverDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "PickupDate")
    private Date pickupDate;

    @Column(name = "DeliverAddress")
    private String deliverAddress;

    @Column(name = "RecipientName")
    private String recipientName;

    @Column(name = "RecipientPhone")
    private String recipientPhone;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss EE") // 在 Java 環境內的時間格式(輸入時調整)
    @Column(name = "PaymentTime")
    private Date paymentTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders", cascade = CascadeType.ALL )
    private Set<OrdersDetail> ordersDetails = new HashSet<OrdersDetail>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    @JsonIgnore
    private User user;
    //↑User的@ManyToOne宣告了2次?

    public void add(OrdersDetail item) {

        if (item != null) {
            if (ordersDetails == null) {
                ordersDetails = new HashSet<>();
            }

            ordersDetails.add(item);
            item.setOrders(this);
        }
    }
}
