package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Category")
public class Category {

    @Id
    @Column(name = "CategoryID", nullable = false, length = 36)
    private String categoryId;

    @Column(name = "CategoryName", nullable = false, length = 50)
    private String categoryName;


    @OneToMany(mappedBy = "Category")
    private Set<Product> products = new HashSet<>();

    public Category() {
    }

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}
