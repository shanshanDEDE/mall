package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @Column(name = "ProductID", nullable = false, unique = true)
    private String productID;

    @Column(name = "ProductDescription", nullable = false)
    private String productDescription;

    @Column(name = "ProductName", nullable = false)
    private String productName;

    // @ManyToOne(fetch = FetchType.LAZY)   //延遲加載
    @Column(name = "CategoryID", nullable = false)
    private String categoryID;

    @Column(name = "Price", nullable = false)
    private int price;

    @Column(name = "Capacity")
    private String capacity;

    @Column(name = "Chip")
    private String chip;

    @Column(name = "Wifi")
    private String wifi;

    @Column(name = "Size")
    private String size;

    @Column(name = "CPU")
    private String cpu;

    @Column(name = "Memory")
    private String memory;

    @Column(name = "Disk")
    private String disk;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SetupDate", nullable = false)
    private Date setupDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifyDate", nullable = false)
    private Date modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)   //延遲加載
    @JoinColumn(name = "CategoryID", nullable = false)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Product", cascade = CascadeType.ALL)
    private Set<ProductSpec> productSpecs= new HashSet<>();
}
