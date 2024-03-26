package com.willy.malltest.repository;

import com.willy.malltest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product p INNER JOIN p.Category c WHERE c.categoryId = :ID")
    List<Product> findByCategoryCategoryID(String ID);

    List<Product> findByProductName(String productName);

    Product findProductsByProductID(String productID);




}
