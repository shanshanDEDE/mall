package com.willy.malltest.service;

import com.willy.malltest.dto.ProductDto;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductPhoto;

import com.willy.malltest.repository.CategoryRepository;
import com.willy.malltest.repository.ProductPhotoRepository;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.repository.CategoryRepository;
import com.willy.malltest.repository.ProductRepository;
import com.willy.malltest.repository.ProductSpecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

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
    public List<Product> getProductByCategoryId(String categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    };

    public Product findProductById(String productId) {
        return productRepository.findById(productId).get();
    }
    public Product insertProduct( Product product) {

        return productRepository.save(product);
    }


    public List<ProductSpec> findProductSpecByProductId(String productId) {

        return productSpecRepository.findProductSpecByProductId(productId);
    }

   public Product findProductByProductId(String productId){

       return productRepository.findProductsByProductId(productId);
   }

    public void saveProduct(Product product) {
        // 实现保存产品到数据库的逻辑
        productRepository.save(product);
    }

    // 根據頁碼搜尋商品和二次封裝
    public Page<ProductDto> findProductByPage(Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 9);
        Page<Product> products = productRepository.findAll(page);

        Page<ProductDto> productDtos = products.map(p -> {
            ProductDto pt = new ProductDto();
            BeanUtils.copyProperties(p, pt);

            List<ProductPhoto> productPhotos = p.getProductSpecs().get(0).getProductPhotos();
            if (productPhotos != null && productPhotos.size() != 0) {
                ProductPhoto firstPhoto = productPhotos.get(0);
                pt.setPhotoId(firstPhoto.getPhotoId());
            }
            return pt;
        });
        return productDtos;
    }

    // 根據圖片ID搜尋商品圖片
    public byte[] findProductPhotoById(Integer id) {
        ProductPhoto productPhoto = productPhotoRepository.findById(id).get();
        if (productPhoto == null) {
            return null;
        }
        String filePath = productPhoto.getPhotoFile();
        try {
            // 讀取檔案內容並返回
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

