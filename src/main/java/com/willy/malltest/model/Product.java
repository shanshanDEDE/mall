package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column
    private String productId;

    @Column
    private String productDescription;

    @Column
    private String productName;

//    @Column(name = "CategoryID", nullable = false)
//    private String categoryID;

    @Column
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

    @Column
    private String productDisk;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date setupDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modifyDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) //延遲加載
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductSpec> productSpecs = new HashSet<>();
}
