package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_photo")
public class ProductPhoto {

    @Id
    @Column(name = "photo_id", nullable = false, unique = true)
    private Integer photoID;

    @Column(name = "photo_file",nullable = false)
    private String photoFile;

    @ManyToOne
    @JoinColumn (name = "spec_id")
    private ProductSpec productPhotoSpec;

}