package com.willy.malltest.controller;


import com.willy.malltest.dto.ProductDto;
import com.willy.malltest.model.*;
import com.willy.malltest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.thymeleaf.util.StringUtils.substring;

@RestController
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:5173","http://localhost:3000", "http://127.0.0.1:5173"})
public class ProductController {

    @Autowired
    private ProductService productService;
    private Product product;


    @GetMapping("/products/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

//    @GetMapping("/products/getProductByCategoryId")
//
//    public List<Product> getProductByCategoryId(@RequestParam String categoryId) {
//        return productService.getProductByCategoryId(categoryId);
//    }

    @GetMapping("/products/getProductByCategoryId")
    public List<Product> getProductByCategoryId(@RequestParam String categoryId) {
        List<Product> products = productService.getProductByCategoryId(categoryId);
        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : products) {
//            && product.getSalesStatus() == 1
            if (product.getSalesStatus() != null ) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }

    @GetMapping("/products/getProductById")
    public Product getProductById(String productId) {
        return productService.findProductById(productId);
    }

    @PostMapping("/products/insertPhone")
    public Product insertPhone(@RequestBody Product p) {
        Category cat = new Category("A", "iPhone");
        p.setCategory(cat);
        System.out.println(new Date());
        p.setSetupDate(new Date());
        p.setModifyDate(new Date());
        p.setSalesStatus(1);
        return productService.insertProduct(p);
    }

    @PostMapping("/products/insertMac")
    public Product insertMac(@RequestBody Product p) {
        Category cat = new Category("B", "Mac");
        p.setCategory(cat);
        System.out.println(new Date());
        p.setSetupDate(new Date());
        p.setModifyDate(new Date());
        p.setSalesStatus(1);
        return productService.insertProduct(p);
    }

    @PostMapping("/products/insertPad")
    public Product insertPad(@RequestBody Product p) {
        Category cat = new Category("C", "iPad");
        p.setCategory(cat);
        System.out.println(new Date());
        p.setSetupDate(new Date());
        p.setModifyDate(new Date());
        p.setSalesStatus(1);
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
//    @GetMapping("/products/getProductByCategoryId")
//    public List<Product> getProductByCategoryId(@RequestParam String categoryId) {
//        return productService.getProductByCategoryId(categoryId);
//    }

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


    // 獲取所有產品圖片的 Controller 端
    @GetMapping("/photos/image/{photoId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Integer photoId) {

        ProductPhoto photos = productService.findProductPhotoByPhotoId(photoId);

        byte[] photoByte = photos.getPhotoFile();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<byte[]>(photoByte, headers, HttpStatus.OK);
    }

    @PutMapping("/products/productSalesStatus")
    public Product productSalesStatus(@RequestParam String productId) {
        Product product = productService.findProductById(productId);
        if (product.getSalesStatus() == 1) {
            product.setSalesStatus(0);
            product.setModifyDate(new Date());
        } else {
            product.setSalesStatus(1);
            product.setModifyDate(new Date());
        }

        productService.saveProduct(product);
        return product;
    }

    @GetMapping("/showAll")
    public ResponseEntity<MultiValueMap<String, Object>> showAllPhotos() {
        List<ProductPhoto> photos = productService.findAllProductPhotos();
        if (photos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (ProductPhoto photo : photos) {
            byte[] photoByte = photo.getPhotoFile();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            body.add("photo", new HttpEntity<>(photoByte, headers));
        }
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/products/insertProductSpec")
    public String insertProductSpec(@RequestBody ProductSpec productSpec) {
        productService.insertProductSpec(productSpec);
       String specId=productSpec.getSpecId();
        return "success insert productSpec";
    }
    @PutMapping("/products/updateProductSpec")
    public String updateProductSpec(@RequestParam String specId, @RequestParam Integer stockQuantity) {
        ProductSpec productSpec = productService.findProductSpecBySpecId(specId);
        productSpec.setStockQuantity(stockQuantity);
        productService.updateProductSpec(productSpec);
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
    @GetMapping("/products/findProductSpecBySpecId/{specId}")
    public ProductSpec findColorBySpecId(@PathVariable String specId) {
        return productService.findProductSpecBySpecId(specId);
    }
    @DeleteMapping("/products/deleteProduct")
    public String deleteProduct(@RequestParam String productId) {
        productService.deleteProduct(productId);
        return "success delete product";
    }
    @DeleteMapping("/products/deleteProductSpec")
    public String deleteProductSpec(@RequestParam String specId) {
        productService.deleteProductSpec(specId);
        return "success delete productSpec";
    }

    @PutMapping("/products/changeStockQuantity")
    public String changeStockQuantity(@RequestParam String specId, @RequestParam Integer stockQuantity) {
        productService.changeStockQuantity(specId, stockQuantity);

        return "success change stockQuantity";
    }

    @PutMapping("/products/changeSpecPhoto")
    public String changeSpecPhoto(@RequestParam String specId, @RequestParam MultipartFile file) throws IOException {
        productService.updateSpecPhoto(specId, file);
        return "success change specPhoto";

    }

}