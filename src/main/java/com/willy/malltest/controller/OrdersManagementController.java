package com.willy.malltest.controller;

import com.willy.malltest.model.Orders;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersManagementController {

    @Autowired
    private OrdersRepository ordersRepo;

    @GetMapping("/orders/findAll")
    public List<Orders> findAllOrders() {
        return ordersRepo.findAll();
    }

    @PostMapping("/orders/insert")
    public List<Orders> insert(@RequestBody List<Orders> requesList){
        return ordersRepo.saveAll(requesList);
    }

    @PostMapping("/orders/insert2")
    public Orders insert2(@RequestBody Orders ord) {
        User user = new User();
        ord.setUser(user);
        System.out.println(ord.getUser());
        return ordersRepo.save(ord);
    }

}
