package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "product_spec")
public class ProductSpec {

    @Id
    @Column(name = "spec_id")
    private String specId;

    @Column
    private String color;

    @Column
    private int stockQuantity;


    @OneToMany(mappedBy = "productSpec")
    private Set<CartItems> cartItems = new HashSet<>();

    @OneToMany(mappedBy = "productSpec")
    private Set<ProductPhoto> productPhotos = new HashSet<>();

    @OneToMany(mappedBy = "productSpec")
    private Set<Track> tracks = new HashSet<>();

    @OneToMany(mappedBy = "productSpec")
    private Set<OrdersDetail> ordersDetails = new HashSet<>();


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}