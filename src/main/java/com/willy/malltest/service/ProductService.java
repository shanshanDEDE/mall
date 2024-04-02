package com.willy.malltest.service;

import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.repository.CategoryRepository;
import com.willy.malltest.repository.ProductPhotoRepository;
import com.willy.malltest.repository.ProductRepository;
import com.willy.malltest.repository.ProductSpecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;
    @Autowired
    private ProductSpecRepository productSpecRepository;

    @Autowired
    private ProductPhotoRepository productPhotoRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //
    public List<Product> getProductByCategoryId(String categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    public Product findProductById(String productId) {
        return productRepository.findById(productId).get();
    }

    public Product insertProduct(Product product) {
        return productRepository.save(product);
    }

//    public List<ProductSpec> findProductSpecByProductId(String productId) {
//
//        return productSpecRepository.findProductSpecByProductId(productId);
//    }

    public Product findProductByProductId(String productId) {

        return productRepository.findProductsByProductId(productId);
    }

    public void saveProduct(Product product) {
        // 实现保存产品到数据库的逻辑
        productRepository.save(product);
    }

    public List<ProductSpec> findProductSpecByProductId(String productId) {
        Product product = productRepository.findProductsByProductId(productId);
        return productSpecRepository.findProductSpecByProduct(product);


    }

    public List<ProductPhoto> findProductPhotoByproductSpecId(String productSpecId) {
        ProductSpec productSpec=productSpecRepository.findProductSpecBySpecId(productSpecId);


        return productPhotoRepository.findProductPhotoByProductSpec(productSpec);
    }

    public ProductPhoto insertProductPhoto(ProductPhoto productPhoto) {

        return productPhotoRepository.save(productPhoto);
    }

    public  List<ProductPhoto> findAllProductPhotos() {
        return  productPhotoRepository.findAll();
    }

    public  ProductPhoto findProductPhotoByPhotoId(Integer photoId) {
        Optional<ProductPhoto> optional = productPhotoRepository.findById(photoId);

        return optional.orElse(null);
    }

    public ProductSpec findProductSpecBySpecId(String specId) {

        return productSpecRepository.findProductSpecBySpecId(specId);
    }
}

