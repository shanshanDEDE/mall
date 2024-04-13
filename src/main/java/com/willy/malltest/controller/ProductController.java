package com.willy.malltest.controller;


import com.willy.malltest.dto.ProductDto;
import com.willy.malltest.model.Category;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:5173/", "http://127.0.0.1:5173"})
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }



    @GetMapping("/products/getProductById")
    public Product getProductById(String productId) {
        return productService.findProductById(productId);
    }

//    @PostMapping("/products/insertProduct")
//    public Product insertProduct(@RequestBody Product product) {
//        return productService.insertProduct(product);
//    }



    @PostMapping("/products/insertPhone")
    public Product insertPhone(@RequestBody Product p) {
        Category cat = new Category("A", "iPhone");
        p.setCategory(cat);
        System.out.println(new Date());
        p.setSetupDate(new Date());
        p.setModifyDate(new Date());
        return productService.insertProduct(p);
    }

    @PostMapping("/products/insertMac")
    public Product insertMac(@RequestBody Product p) {
        Category cat = new Category("B", "Mac");
        p.setCategory(cat);
        System.out.println(new Date());
        p.setSetupDate(new Date());
        p.setModifyDate(new Date());
        return productService.insertProduct(p);
    }

    @PostMapping("/products/insertPad")
    public Product insertPad(@RequestBody Product p) {
        Category cat = new Category("C", "iPad");
        p.setCategory(cat);
        System.out.println(new Date());
        p.setSetupDate(new Date());
        p.setModifyDate(new Date());
        return productService.insertProduct(p);
    }

    @PutMapping("/products/updateProduct/{productID}")
    public Product updateProduct(@PathVariable String productId, @RequestBody Product updatedProduct) {
        System.out.println(productId);
        // 不需要转换为字符串
        Product existingProduct = productService.findProductByProductId(productId);
        System.out.println(existingProduct);
        System.out.println("-------------------------------------------------");
        Category cat = new Category("A", "iPhone");

        if (existingProduct != null) {
            existingProduct.setCategory(cat);
            existingProduct.setProductDescription(updatedProduct.getProductDescription());
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCapacity(updatedProduct.getCapacity());
            existingProduct.setChip(updatedProduct.getChip());
            existingProduct.setWifi(updatedProduct.getWifi());
            existingProduct.setSize(updatedProduct.getSize());
            existingProduct.setCpu(updatedProduct.getCpu());
            existingProduct.setMemory(updatedProduct.getMemory());
            existingProduct.setProductDisk(updatedProduct.getProductDisk());
            existingProduct.setModifyDate(new Date());
            productService.saveProduct(existingProduct);
            System.out.println("Product updated successfully: " + existingProduct);
        }
        System.out.println(existingProduct);
        return existingProduct;
    }

    @GetMapping("/products/findProductByProductId")
    public Product findProductByProductId(@RequestParam String productId) {
        return productService.findProductByProductId(productId);
    }

    @GetMapping("/products/findProductByProductId1")
    public Product findProductByProductId1(@RequestParam String productId) {
        // 首先确认传递给 findProductByProductId 方法的 productId 值是否正确
        System.out.println("传递的 productId 值为：" + productId);

        // 调用 productService 中的 findProductByProductId 方法来查找产品
        Product existingProduct = productService.findProductByProductId(productId);

        // 查看返回的产品对象是否为空
        if (existingProduct != null) {
            System.out.println("找到匹配的产品：" + existingProduct);
        } else {
            System.out.println("未找到匹配的产品");
        }

        // 返回产品对象
        return existingProduct;
    }

    @GetMapping("/products/findProductSpecByProductId")
    public List<ProductSpec> findProductSpecByProductId(String productId) {
        return productService.findProductSpecByProductId(productId);
    }
    // 根據商品頁面取得商品Dto
    @GetMapping("/products/{pageNumber}")
    public Page<ProductDto> findProductByPage(@PathVariable Integer pageNumber) {
        return productService.findProductByPage(pageNumber);
    }
    //模糊查詢產品
    @GetMapping("/products/findFilterProductByPage/{pageNumber}")
    public Page<ProductDto> findFilterProductByPage(@PathVariable Integer pageNumber, @RequestParam String productName){
        return productService.findFilterProductByPage(pageNumber, productName);
    }

    @GetMapping(path = "/product/photo/{id}", produces = "image/*")
    public byte[] findProductPhotoById(@PathVariable Integer id) {

        return productService.findProductPhotoById(id)	;
    }
    @GetMapping("/products/getProductByCategoryId")

    public List<Product> getProductByCategoryId(@RequestParam String categoryId) {
        return productService.getProductByCategoryId(categoryId);
    }

    @GetMapping("/products/findProductsByCategoryId")   //搜尋不同category的商品用DTO
    public Page<ProductDto> findProductsByCategoryId(@RequestParam String categoryId, @RequestParam Integer pageNumber) {
        Page<ProductDto> productDtos = productService.findProductsByCategoryId(categoryId, pageNumber);
        return productDtos;
    }

    @PostMapping("/products/insertProductPhoto")
    public String insertProductPhoto(@RequestParam String specId, @RequestBody MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return "ProductPhoto is empty";
            }
            ProductPhoto productPhoto = new ProductPhoto();
            productPhoto.setPhotoFile(file.getBytes());
            productPhoto.setProductSpec(productService.findProductSpecBySpecId(specId));
            productService.insertProductPhoto(productPhoto);
        } catch (IOException e) {
            e.printStackTrace(); // 您可能希望適當地記錄例外情況或根據應用程式的需要進行處理
            return "error"; // 假設 "error" 是表示發生錯誤的邏輯視圖名稱
        }
        return "success";
    }

    //用specId調用照片
    @GetMapping(path = "/productSpec/photo/{specId}", produces = "image/*")
    public byte[] findProductPhotoBySpecId(@PathVariable String specId) {

        return productService.findProductPhotoByProductSpecId(specId)	;
    }
    //模糊查詢分類產品名稱
    @GetMapping("/products/searchProduct/{pageNumber}")
    public Page<ProductDto> findFilterCategoryAndProductByPage(@PathVariable Integer pageNumber, @RequestParam String productName, @RequestParam String categoryId) {
        return productService.findFilterCategoryAndProductByPage(pageNumber, productName, categoryId);
    }



}