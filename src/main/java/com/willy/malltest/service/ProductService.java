package com.willy.malltest.service;

import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductSpec;
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
    @Autowired
    private ProductSpecRepository productSpecRepository;



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//
    public List<Product> getProductByCategoryID(String categoryID) {
        return productRepository.findByCategoryCategoryID(categoryID);
    }

    public Product findProductByID(String productID) {
        return productRepository.findById(productID).get();
    }
    public Product insertProduct( Product product) {
        return productRepository.save(product);
    }

    public List<ProductSpec> findProductSpecByProductID(String productID) {

        return productSpecRepository.findProductSpecByProductId(productID);
    }

   public Product findProductByProductID(String productID){

       return productRepository.findProductsByProductID(productID);
   }

    public void saveProduct(Product product) {
        // 实现保存产品到数据库的逻辑
        productRepository.save(product);
    }

}

