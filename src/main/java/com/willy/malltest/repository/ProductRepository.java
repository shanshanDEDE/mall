package com.willy.malltest.repository;

import com.willy.malltest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
//    List<Product> findByCategoryId(String categoryID);

}
