package com.willy.malltest.repository;


import com.willy.malltest.model.ProductSpec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecRepository extends JpaRepository<ProductSpec, String> {


//    public List<ProductSpec> findProductSpecByProductId(String productId);

    }

