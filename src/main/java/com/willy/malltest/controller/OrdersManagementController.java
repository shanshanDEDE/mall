package com.willy.malltest.controller;

import com.willy.malltest.dto.OrdersDetailDTO;
import com.willy.malltest.dto.ReceiveOrdersDTO;
import com.willy.malltest.dto.ReceiveOrdersDetailDTO;
import com.willy.malltest.model.Orders;
import com.willy.malltest.repository.OrdersDetailRepository;
import com.willy.malltest.service.OrdersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class OrdersManagementController {

    @Autowired
    private OrdersManagementService ordersManagementService;

    @Autowired
    private OrdersDetailRepository ordersDetailRepo;

    @GetMapping("/orders/findAll")
    public List<Orders> findAllOrders() {
        return ordersManagementService.findAll();
    }

    @GetMapping("/orders/findAllOrdersDetailDTOs")
    public List<OrdersDetailDTO> findAllOrdersDetailDTOs() {

        return ordersManagementService.findOrdersDetailDTOs();
    }

    @PostMapping("/orders/insertOrdersDetail")
    public String insertOrdersDetail(@RequestBody ReceiveOrdersDetailDTO receiveOrdersDetailDTO) {
        return ordersManagementService.insertOrdersDetail(receiveOrdersDetailDTO);
    }

    @PostMapping("/orders/insertOrders")
    public ResponseEntity<Map<String, String>> insertOrders(@RequestBody ReceiveOrdersDTO receiveOrdersDTO) {
        String orderId =  ordersManagementService.insertOrders(receiveOrdersDTO);
        if (orderId != null) {
            Map<String, String> response = new HashMap<>();
            response.put("orderId", orderId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/orders/insertOrdersAndDetail")
//    public String insertOrdersAndDetail(@RequestBody ReceiveOrdersAndDetailDTO receiveOrdersAndDetailDTO) {
//        return ordersManagementService.insertOrdersAndDetail(receiveOrdersAndDetailDTO);
//    }


//    @PostMapping("/orders/reorder")
//    public Orders reorderByOrderId(@RequestParam Integer orderId) {
//        return ordersManagementService.reorderByOrderId(orderId);
//    }

    @PutMapping("/orders/updateOrder/{orderId}")
    public Orders updateOrderByOrderId(@PathVariable Integer orderId, @RequestBody ReceiveOrdersDTO receiveOrdersDTO) {
        return ordersManagementService.updateOrderByOrderId(orderId, receiveOrdersDTO);
    }


//    @PutMapping("/orders/edit")
//    public Integer editNameById(String recipientName, Integer orderId) {
//        return ordersManagementService.updateNameById(recipientName, orderId);
//    }

    @PutMapping("/orders/changeOrderStatus")
    public String changeOrderStatus(@RequestParam Integer OrderId) {
        ordersManagementService.updateOrderStatusByOrderId(OrderId);
        return "ok";

    }
}