package com.willy.malltest.repository;

import com.willy.malltest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product p INNER JOIN p.Category c WHERE c.categoryId = :categoryID")
    List<Product> findByCategoryCategoryID(String categoryID);
}
