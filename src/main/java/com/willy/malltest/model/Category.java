package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class Category {

    @Id
    @Column
    private String categoryId;

    @Column
    private String categoryName;


    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    public Category() {
    }

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}
