package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "return_details")
public class ReturnDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer returnDetailId;

    @OneToOne
    @JoinColumn(name = "orders_detail_id")
    private OrdersDetail ordersDetail;


    private String returnReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_id")
    private Returns returns;
}
