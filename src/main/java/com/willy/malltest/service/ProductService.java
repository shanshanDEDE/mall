package com.willy.malltest.service;

import com.willy.malltest.model.Product;
import com.willy.malltest.repository.CategoryRepository;
import com.willy.malltest.repository.ProductRepository;
import com.willy.malltest.repository.ProductSpecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductSpecRepository productSpecRepository;



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//
    public List<Product> getProductByCategoryID(String categoryID) {
        return productRepository.findByCategoryCategoryID(categoryID);
    };
//
//    public Product getProductByID(String productID) {
//        return productRepository.findById(productID).get();
//    }
    public Product insertProduct( Product product) {
        return productRepository.save(product);
    }



}

