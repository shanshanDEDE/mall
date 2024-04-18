package com.willy.malltest.service;

import com.willy.malltest.dto.ProductDto;
import com.willy.malltest.model.Category;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.repository.CategoryRepository;
import com.willy.malltest.repository.ProductPhotoRepository;
import com.willy.malltest.repository.ProductRepository;
import com.willy.malltest.repository.ProductSpecRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Optional;

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
        Category category = categoryRepository.findByCategoryId(categoryId);
        return productRepository.findProductsByCategory(category);
    }

    public Product findProductById(String productId) {
        return productRepository.findById(productId).get();
    }

    public Product insertProduct(Product product) {
        return productRepository.save(product);
    }


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

    // 根據頁碼搜尋商品和二次封裝
    public Page<ProductDto> findProductByPage(Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 6);

        Page<Product> products = productRepository.findSalesStatusOnSale(page);

        Page<ProductDto> productDtos = products.map(p -> {
            ProductDto pt = new ProductDto();
            BeanUtils.copyProperties(p, pt);

            List<ProductSpec> productSpecs = p.getProductSpecs();
            if (productSpecs != null && !productSpecs.isEmpty()) {
                List<String> specIds = new ArrayList<>();
                productSpecs.forEach(spec-> specIds.add(spec.getSpecId()));
                pt.setSpecIds(specIds);

                List<ProductPhoto> productPhotos = productSpecs.get(0).getProductPhotos();
                if (productPhotos != null && productPhotos.size() != 0) {
                    ProductPhoto firstPhoto = productPhotos.get(0);
                    pt.setPhotoId(firstPhoto.getPhotoId());
                }
            }
            return pt;
        });
        return productDtos;
    }
//模糊查詢產品名稱 用DTO封裝
    public Page<ProductDto> findFilterProductByPage(Integer pageNumber, String productName) {
        Pageable page = PageRequest.of(pageNumber, 6);
        Page<Product> products = productRepository.findByProductName(productName, page);

        return products.map(p -> {
            ProductDto pt = new ProductDto();
            BeanUtils.copyProperties(p, pt);

            List<ProductSpec> productSpecs = p.getProductSpecs();    //加入productSpec
            if (productSpecs != null && !productSpecs.isEmpty()) {
                List<String> specIds = new ArrayList<>();
                productSpecs.forEach(spec-> specIds.add(spec.getSpecId()));
                pt.setSpecIds(specIds);

                List<ProductPhoto> productPhotos = productSpecs.get(0).getProductPhotos();
                if (productPhotos != null && !productPhotos.isEmpty()) {
                    ProductPhoto firstPhoto = productPhotos.get(0);
                    pt.setPhotoId(firstPhoto.getPhotoId());
                }
            }
            return pt;
        });
    }


    // 根據圖片ID搜尋商品圖片
    public byte[] findProductPhotoById(Integer id) {
        ProductPhoto productPhoto = productPhotoRepository.findById(id).get();
        if (productPhoto == null) {
            return null;
        }
        return productPhoto.getPhotoFile();

    }
    // 根據specId搜尋第一張商品圖片
    public byte[] findProductPhotoByProductSpecId(String specId) {
        ProductSpec productSpec = productSpecRepository.findById(specId).get();
        List<ProductPhoto> productPhotos = productPhotoRepository.findByProductSpec(productSpec);
        if (productPhotos == null) {
            return null;
        }
        return productPhotos.get(0).getPhotoFile();

    }

    // 根据产品类别ID分页搜索商品并进行二次封装
    public Page<ProductDto> findProductsByCategoryId(String categoryId, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 6);
        Page<Product> products = productRepository.findByCategoryCategoryId(categoryId, page); // 根据产品类别ID进行过滤

        Page<ProductDto> productDtos = products.map(p -> {
            ProductDto pt = new ProductDto();
            pt.setProductId(p.getProductId()); // 设置productId
            pt.setProductName(p.getProductName()); // 设置productName
            pt.setPrice(p.getPrice());
            pt.setProductDescription(p.getProductDescription());

            // 设置photoId
            List<ProductSpec> productSpecs = p.getProductSpecs();
            if (productSpecs != null && !productSpecs.isEmpty()) {
                List<String> specIds = new ArrayList<>();
                productSpecs.forEach(spec-> specIds.add(spec.getSpecId()));
                pt.setSpecIds(specIds);

                List<ProductPhoto> productPhotos = productSpecs.get(0).getProductPhotos();
                if (productPhotos != null && productPhotos.size() != 0) {
                    ProductPhoto firstPhoto = productPhotos.get(0);
                    pt.setPhotoId(firstPhoto.getPhotoId());
                }
            }
            return pt;
        });
        return productDtos;
    }

    public ProductPhoto insertProductPhoto(ProductPhoto productPhoto) {

        return productPhotoRepository.save(productPhoto);
    }

    public List<ProductPhoto> findAllProductPhotos() {
        return productPhotoRepository.findAll();
    }

    public ProductSpec insertProductSpec(ProductSpec productSpec) {
        String specId=productSpec.getSpecId();
        Product product=productRepository.findProductsByProductId(specId.substring(0,5));
        productSpec.setDeleted(false);
        productSpec.setProduct(product);
        return productSpecRepository.save(productSpec);
    }

    public ProductPhoto findProductPhotoByPhotoId(Integer photoId) {
        Optional<ProductPhoto> optional = productPhotoRepository.findById(photoId);

        return optional.orElse(null);
    }

    public ProductSpec findProductSpecBySpecId(String specId) {
        return productSpecRepository.findProductSpecBySpecId(specId);
    }
    //模糊查詢分類的產品名稱 用DTO封裝
    public Page<ProductDto> findFilterCategoryAndProductByPage(Integer pageNumber, String productName, String categoryId) {
        Pageable page = PageRequest.of(pageNumber, 6);
        Page<Product> products = productRepository.findByCategoryAndProductName(categoryId, productName, page);
        return products.map(p -> {
            ProductDto pt = new ProductDto();
            BeanUtils.copyProperties(p, pt);
            List<ProductSpec> productSpecs = p.getProductSpecs();    //加入productSpec
            if (productSpecs != null && !productSpecs.isEmpty()) {
                List<String> specIds = new ArrayList<>();
                productSpecs.forEach(spec-> specIds.add(spec.getSpecId()));
                pt.setSpecIds(specIds);
                List<ProductPhoto> productPhotos = productSpecs.get(0).getProductPhotos();
                if (productPhotos != null && !productPhotos.isEmpty()) {
                    ProductPhoto firstPhoto = productPhotos.get(0);
                    pt.setPhotoId(firstPhoto.getPhotoId());
                }
            }
            return pt;
        });
    }
    public ProductSpec updateProductSpec(ProductSpec productSpec) {
        return productSpecRepository.save(productSpec);
    }

    public void deleteProduct(String productId) {
        Product product = productRepository.findProductsByProductId(productId);
        productRepository.delete(product);
    }

    public void deleteProductSpec(String specId) {
        ProductSpec productSpec = productSpecRepository.findProductSpecBySpecId(specId);
        productSpecRepository.delete(productSpec);
    }
    public void changeStockQuantity(String specId, int quantity) {
        Product product = productRepository.findProductsByProductId(specId.substring(0,5));
        ProductSpec productSpec = productSpecRepository.findProductSpecBySpecId(specId);
        productSpec.setProduct(product);
        productSpec.setStockQuantity(quantity);
        productSpecRepository.save(productSpec);

    }


    public String updateSpecPhoto(String specId, MultipartFile file) throws IOException {
        ProductSpec productSpec = productSpecRepository.findProductSpecBySpecId(specId);
        List<ProductPhoto> productPhotos = productPhotoRepository.findByProductSpec(productSpec);
        productPhotos.get(0).setPhotoFile(file.getBytes());
        productPhotoRepository.save(productPhotos.get(0));
        return "success update photo";
    }
}

