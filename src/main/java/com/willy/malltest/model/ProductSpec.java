package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "product_spec")
public class ProductSpec {

    @Id
    @Column(name="spec_id", nullable = false, unique = true)
    private String specId;

    @Column(name="product_id" ,insertable = false, updatable = false, nullable = false)
    private String productId;

    @Column( nullable = false)
    private String color;

    @Column(name="stock_quantity" ,nullable = true)
    private int stockQuantity;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productSpec", cascade = CascadeType.ALL)
    private List<ProductPhoto> productPhotos= new ArrayList<>();

    public ProductSpec() {
        super();
    }

    public ProductSpec(String productSpecId) {
        super();
    }
}