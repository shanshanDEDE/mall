package com.willy.malltest.controller;

import com.willy.malltest.dto.OrdersDetailDTO;
import com.willy.malltest.dto.ReceiveOrdersAndDetailDTO;
import com.willy.malltest.dto.ReceiveOrdersDTO;
import com.willy.malltest.dto.ReceiveOrdersDetailDTO;
import com.willy.malltest.model.Orders;
import com.willy.malltest.model.OrdersDetail;
import com.willy.malltest.model.Product;
import com.willy.malltest.repository.OrdersDetailRepository;
import com.willy.malltest.service.OrdersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//@RequestMapping("/api")
@RestController
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://127.0.0.1:5173" })
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

//    @GetMapping("/orders/findODByOrderId")
//    public List<OrdersDetail> findODByOrderId(@RequestParam Integer orderId) {
//        return ordersDetailRepo.findByOrderId(orderId);
//    }

//    @PostMapping("/orders/insert")
//    public List<Orders> insert(@RequestBody List<Orders> requesList) {
//        return ordersManagementService.saveAll(requesList);
//    }

//    @PostMapping("/orders/insert2")
//    public Orders insert2(@RequestBody Orders orders) {
//        return ordersManagementService.insert(orders);
//    }

    @PostMapping("/orders/insertOrdersDetail")
    public String insertOrdersDetail(@RequestBody ReceiveOrdersDetailDTO receiveOrdersDetailDTO) {
        return ordersManagementService.insertOrdersDetail(receiveOrdersDetailDTO);
    }

    @PostMapping("/orders/insertOrders")
    public String insertOrders(@RequestBody ReceiveOrdersDTO receiveOrdersDTO) {
        return ordersManagementService.insertOrders(receiveOrdersDTO);
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

    @PutMapping("/orders/updateOrdersDetail/{ordersDetailId}")
    public String updateOrdersDetailByOrdersDetailId(@PathVariable Integer ordersDetailId, @RequestBody ReceiveOrdersDetailDTO receiveOrdersDetailDTO) {
        return ordersManagementService.updateOrdersDetailByOrdersDetailId(ordersDetailId, receiveOrdersDetailDTO);
    }

    @DeleteMapping("/orders/deleteOrdersDetail/{ordersDetailId}")
    public String deleteOrdersDetailByOrdersDetailId(@PathVariable Integer ordersDetailId) {
        ordersManagementService.deleteOrdersDetailByOrdersDetailId(ordersDetailId);
        return "success delete ordersDetail";
    }

//    @PutMapping("/orders/edit")
//    public Integer editNameById(String recipientName, Integer orderId) {
//        return ordersManagementService.updateNameById(recipientName, orderId);
//    }

//    @PutMapping("/orders/updateOrderStatus/{OrderId}")
//    public String updateOrderStatusByOrderIdXX(@PathVariable Integer OrderId) {
//        ordersManagementService.updateOrderStatusByOrderIdXX(OrderId);
//        return "update orderStatus ok";
//    }


    @PutMapping("/orders/updateOrderStatusByOrderId/{orderId}")
    public Orders updateOrderStatusByOrderId(@PathVariable Integer orderId, @RequestParam String orderStatus) {
        return ordersManagementService.updateOrderStatusByOrderId(orderId, orderStatus);
    }

}