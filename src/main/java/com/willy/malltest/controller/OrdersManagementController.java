package com.willy.malltest.controller;

import com.willy.malltest.model.Orders;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.OrdersRepository;
import com.willy.malltest.service.OrdersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://localhost:127.0.0.1:5173" })
public class OrdersManagementController {

    @Autowired
    private OrdersManagementService ordersManagementService;

    @GetMapping("/orders/findAll")
    public List<Orders> findAllOrders() {
        return ordersManagementService.findAll();
    }

//    @PostMapping("/orders/insert")
//    public List<Orders> insert(@RequestBody List<Orders> requesList) {
//        return ordersManagementService.saveAll(requesList);
//    }

    @PostMapping("/orders/insert2")
    public Orders insert2(@RequestBody Orders orders) {
//        User user = new User();
//        ord.setUser(user);
//        System.out.println(ord.getUser());
        return ordersManagementService.insert(orders);
    }

    @PutMapping("/orders/edit")
    public Integer editNameById(String recipientName, Integer orderId) {
        return ordersManagementService.updateNameById(recipientName, orderId);
    }

}
