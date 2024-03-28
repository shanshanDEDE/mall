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
    @Column(name = "photo_id")
    private Integer photoID;

    @Lob
    @Column(name = "photo_file")
    private byte[] photoFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id", referencedColumnName = "spec_id")
    private ProductSpec productSpec;


}
