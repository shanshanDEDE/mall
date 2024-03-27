package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders_detail")
public class OrdersDetail {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ordersDetailId;  //PRIMARY KEY identity(1,1),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "spec_id")
    private ProductSpec productSpec;

    @Column
    private int quantity;

    @Column
    private int price;

    @OneToOne(mappedBy = "ordersDetail")
    private ReturnDetails returnDetails;


}
