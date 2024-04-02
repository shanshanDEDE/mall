package com.willy.malltest.controller;


import ch.qos.logback.core.model.Model;
import com.willy.malltest.model.Category;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductPhoto;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:5173/", "http://127.0.0.1:5173"})
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/getProductByCategoryId")

    public List<Product> getProductByCategoryId(@RequestParam String categoryId) {
        return productService.getProductByCategoryId(categoryId);
    }

    @GetMapping("/products/getProductById")
    public Product getProductById(String productId) {
        return productService.findProductById(productId);
    }

//    @PostMapping("/products/insertProduct")
//    public Product insertProduct(@RequestBody Product product) {
//        return productService.insertProduct(product);
//    }

    @PostMapping("/products/insertExm")
    public Product insertExm() {
        Category cat = new Category("A", "iPhone");

        Product pro = new Product();
        pro.setProductId("A1402");
        pro.setProductName("iPhone 14 128G");
        pro.setProductDescription("6.1 吋 2,532 x 1,170pixels 解析度超 Retina XDR 顯示器，搭載 OLED 螢幕面板，支援原彩顯示、電影級 P3 標準廣色域；顯示 HDR 內容時，螢幕亮度最高可達 1,200nits。");
        pro.setCategory(cat);

        pro.setPrice(25900);
        pro.setModifyDate(new Date());
        pro.setSetupDate(new Date());
        return productService.insertProduct(pro);
    }

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

    @PutMapping("/products/updateProduct/{productId}")
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


    @PostMapping("/products/insertProductPhoto")
    public String insertProductPhoto(@RequestParam String specId, @RequestBody MultipartFile file, Model model) {

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


    // 獲取所有產品圖片的 Controller 端
    @GetMapping("/photos/image/{photoId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Integer photoId) {

        ProductPhoto photos = productService.findProductPhotoByPhotoId(photoId);

        byte[] photoByte = photos.getPhotoFile();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<byte[]>(photoByte, headers, HttpStatus.OK);
    }

}

