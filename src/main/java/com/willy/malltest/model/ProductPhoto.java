package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ProductPhoto")
public class ProductPhoto {

    @Id
    @Column(name = "PhotoID", nullable = false, unique = true)
    private Integer photoID;

    @Column(name = "PhotoFile",nullable = false)
    private String photoFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "SpecID")
    private ProductSpec SpecID;

}
