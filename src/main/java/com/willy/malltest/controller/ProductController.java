package com.willy.malltest.controller;


import com.willy.malltest.model.CustomerFeedback;
import com.willy.malltest.model.Product;
import com.willy.malltest.service.CustomerFeedBackService;
import com.willy.malltest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/products/getProductByCategoryID")
    public List<Product> getProductByCategoryID(String categoryID) {
        return productService.getProductByCategoryID(categoryID);
    }
    @GetMapping("/products/getProductByID")
    public Product getProductByID(String productID) {
        return productService.getProductByID(productID);
    }
    @PostMapping("/products/insertProduct")
    public Product insertProduct(@RequestBody Product product) {
        return productService.insertProduct(product);
    }
@GetMapping("/products/insertExm")
    public Product insertExm(){
        Product pro = new Product();
        pro.setProductID("A1402");
        pro.setProductName("iPhone 14 128G");
        pro.setProductDescription("6.1 吋 2,532 x 1,170pixels 解析度超 Retina XDR 顯示器，搭載 OLED 螢幕面板，支援原彩顯示、電影級 P3 標準廣色域；顯示 HDR 內容時，螢幕亮度最高可達 1,200nits。");
        pro.setCategoryID("B");
        pro.setPrice(25900);
        return productService.insertProduct(pro);
    }
}
