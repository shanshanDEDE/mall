package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders_detail")
public class OrdersDetail {

    @Id
    @Column(name = "orders_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ordersDetailId;  //PRIMARY KEY identity(1,1),

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders orders;  //foreign key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id")
    @JsonIgnore
    private ProductSpec productSpec;  //foreign key

    @OneToOne(mappedBy = "ordersDetail")
    private ReturnDetails returnDetails;

}
