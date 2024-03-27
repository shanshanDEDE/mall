package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name="product_id",nullable = false, unique = true)
    private String productId;

    @Column(name="product_description" ,nullable = false)
    private String productDescription;

    @Column(name="product_name" ,nullable = false)
    private String productName;

//    @Column(name = "CategoryID", nullable = false)
//    private String categoryID;

    @Column(nullable = false)
    private int price;

    @Column
    private String capacity;

    @Column
    private String chip;

    @Column
    private String wifi;

    @Column
    private String size;

    @Column
    private String cpu;

    @Column
    private String memory;

    @Column(name = "product_disk")
    private String productDisk;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="setup_date",nullable = false)
    private Date setupDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date", nullable = false)
    private Date modifyDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) //延遲加載
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductSpec> productSpecs= new ArrayList<>();
}
