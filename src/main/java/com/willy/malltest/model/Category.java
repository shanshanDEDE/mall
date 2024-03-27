package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {

    @Id
    @Column(name="category_id" ,nullable = false, length = 36)
    private String categoryId;

    @Column(name="category_name" ,nullable = false, length = 50)
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
