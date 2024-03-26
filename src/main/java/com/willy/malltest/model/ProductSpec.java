package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "ProductSpec")
public class ProductSpec {
    public ProductSpec() {
        super();
    }

    public ProductSpec(String productSpecId) {
        super();
    }
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


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "productSpec", cascade = CascadeType.ALL)
    private List<ProductPhoto> productPhotos = new ArrayList<>();
}