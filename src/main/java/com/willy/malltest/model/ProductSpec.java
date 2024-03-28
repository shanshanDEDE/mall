package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@Entity
@Table(name = "product_spec")
public class ProductSpec {

    @Id
    @Column(name = "spec_id")
    private String specId;

    @Column(name = "color")
    private String color;

    @Column(name = "stock_quantity")
    private int stockQuantity;


    @JsonIgnore
    @OneToMany(mappedBy = "productSpec")
    private List<CartItems> cartItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "productSpec")
    private List<ProductPhoto> productPhotos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "productSpec")
    private List<Track> tracks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "productSpec")
    private List<OrdersDetail> ordersDetails = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}