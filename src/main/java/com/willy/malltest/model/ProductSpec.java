package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID")
    private Product product;



    @OneToMany(mappedBy = "productSpec",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductPhoto> productPhotos = new HashSet<>();}