package com.willy.malltest.service;

import com.willy.malltest.model.Orders;
import com.willy.malltest.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersManagementService {

    @Autowired
    private OrdersRepository ordersRepo;

    public List<Orders> findAll() {
        return ordersRepo.findAll();
    }

    public Orders insert(Orders orders) {
        return ordersRepo.save(orders);
    }

    public Integer updateNameById(String recipientName, Integer orderId) {
        return ordersRepo.updateNameById(recipientName, orderId);
    }

}
