package com.willy.malltest.repository;


import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductSpec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSpecRepository extends JpaRepository<ProductSpec, String> {

    List<ProductSpec> findProductSpecByProduct(Product product);
    ProductSpec findProductSpecBySpecId(String specId);}




