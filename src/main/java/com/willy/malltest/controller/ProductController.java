package com.willy.malltest.controller;


import com.willy.malltest.model.Category;
import com.willy.malltest.model.Product;
import com.willy.malltest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/products/getProductByCategoryID")

    public List<Product> getProductByCategoryID(@RequestParam String categoryID) {
        return productService.getProductByCategoryID(categoryID);
    }

    @GetMapping("/products/getProductByID")
    public Product getProductByID(String productID) {
        return productService.findProductByID(productID);
    }

//    @PostMapping("/products/insertProduct")
//    public Product insertProduct(@RequestBody Product product) {
//        return productService.insertProduct(product);
//    }

    @PostMapping("/products/insertExm")
    public Product insertExm() {
        Category cat = new Category("A", "iPhone");

        Product pro = new Product();
        pro.setProductID("A1402");
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

    @PutMapping("/products/updateProduct/{productID}")
    public Product updateProduct(@PathVariable String productID, @RequestBody Product updatedProduct) {
        System.out.println(productID);
        // 不需要转换为字符串
        Product existingProduct = productService.findProductByProductID(productID);
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

    @GetMapping("/products/findProductByProductID")
    public Product findProductByProductID(@RequestParam String productID) {
        return productService.findProductByProductID(productID);
    }

    @GetMapping("/products/findProductByProductID1")
    public Product findProductByProductID1(@RequestParam String productID) {
        // 首先确认传递给 findProductByProductID 方法的 productID 值是否正确
        System.out.println("传递的 productID 值为：" + productID);

        // 调用 productService 中的 findProductByProductID 方法来查找产品
        Product existingProduct = productService.findProductByProductID(productID);

        // 查看返回的产品对象是否为空
        if (existingProduct != null) {
            System.out.println("找到匹配的产品：" + existingProduct);
        } else {
            System.out.println("未找到匹配的产品");
        }

        // 返回产品对象
        return existingProduct;
    }

}