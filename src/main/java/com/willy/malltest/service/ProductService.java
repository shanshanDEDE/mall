package com.willy.malltest.service;

import com.willy.malltest.dto.ProductDto;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductPhoto;

import com.willy.malltest.repository.CategoryRepository;
import com.willy.malltest.repository.ProductPhotoRepository;
import com.willy.malltest.repository.ProductRepository;
import com.willy.malltest.repository.ProductSpecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductSpecRepository productSpecRepository;
    @Autowired
    private ProductPhotoRepository productPhotoRepository;



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//
    public List<Product> getProductByCategoryID(String categoryID) {
        return productRepository.findByCategoryCategoryID(categoryID);
    };

    public Product findProductByID(String productID) {
        return productRepository.findById(productID).get();
    }
    public Product insertProduct( Product product) {

        return productRepository.save(product);
    }

    // 根據頁碼搜尋商品
    public Page<ProductDto> findProductByPage(Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 9);
        Page<Product> products = productRepository.findAll(page);

        Page<ProductDto> productDtos = products.map(p -> {
            ProductDto pt = new ProductDto();
            BeanUtils.copyProperties(p, pt);

            List<ProductPhoto> productPhotos = p.getProductSpecs().get(0).getProductPhotos();
            if (productPhotos != null && productPhotos.size() != 0) {
                ProductPhoto firstPhoto = productPhotos.get(0);
                pt.setPhotoID(firstPhoto.getPhotoID());
            }
            return pt;
        });
        return productDtos;
    }

    // 根據圖片ID搜尋商品圖片
//    public byte[] findProductPhotoById(String id) {
//        ProductPhoto productPhoto = productPhotoDao.findById(id).get();
//        if (productPhoto == null) {
//            return null;
//        }
//
//        return productPhoto.getProductPhoto();
//    }




}

