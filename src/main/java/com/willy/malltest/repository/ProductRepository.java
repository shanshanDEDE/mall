package com.willy.malltest.repository;

import com.willy.malltest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product p INNER JOIN p.category c WHERE c.categoryId = :Id")
    List<Product> findByCategoryCategoryId(String Id);

    List<Product> findByProductName(String productName);

    Product findProductsByProductId(String productId);






}
