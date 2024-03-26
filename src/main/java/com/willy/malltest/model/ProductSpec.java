package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "ProductSpec")
public class ProductSpec {

    @Id
    @Column(name = "SpecID", nullable = false, unique = true)
    private String specID;

    @Column(name = "ProductID", insertable = false, updatable = false, nullable = false)
    private String productId;

    @Column(name = "Color", nullable = false)
    private String color;

    @Column(name = "StockQuantity", nullable = false)
    private int stockQuantity;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID")
    private Product product;
}