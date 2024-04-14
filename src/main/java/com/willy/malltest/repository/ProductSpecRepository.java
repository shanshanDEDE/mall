package com.willy.malltest.repository;


import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSpecRepository extends JpaRepository<ProductSpec, String> {


  List<ProductSpec> findProductSpecByProduct(Product product);

ProductSpec findProductSpecBySpecId(String specId);


}



