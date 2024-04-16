package com.willy.malltest.service;

import com.willy.malltest.dto.OrdersDetailDTO;
import com.willy.malltest.dto.ReceiveOrdersAndDetailDTO;
import com.willy.malltest.dto.ReceiveOrdersDTO;
import com.willy.malltest.dto.ReceiveOrdersDetailDTO;
import com.willy.malltest.model.Orders;
import com.willy.malltest.model.OrdersDetail;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersManagementService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductSpecRepository productSpecRepository;

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    //此方法目的在於呈現所有訂單明細相關資訊
    public List<OrdersDetailDTO> findOrdersDetailDTOs() {
        List<OrdersDetail> ordersDetails = ordersDetailRepository.findAll();
        List<OrdersDetailDTO> ordersDetailDTOs = new ArrayList<>();//初始化空的OrdersDetailDTO集合

        for (OrdersDetail od : ordersDetails) { //遍歷每個OrdersDetail對象
            OrdersDetailDTO oddto = new OrdersDetailDTO();
            BeanUtils.copyProperties(od, oddto);
            BeanUtils.copyProperties(od.getOrders(), oddto);
            BeanUtils.copyProperties(od.getOrders().getUser(), oddto);
            BeanUtils.copyProperties(od.getProductSpec(), oddto);
            BeanUtils.copyProperties(od.getProductSpec().getProduct(), oddto);
            ordersDetailDTOs.add(oddto);
        }

        return ordersDetailDTOs;
    }

    public String insertOrdersDetail(ReceiveOrdersDetailDTO receiveOrdersDetailDTO) {
        OrdersDetail ordersDetail = new OrdersDetail();
        Orders orders = ordersRepository.findById(receiveOrdersDetailDTO.getOrderId()).orElse(null);
        ProductSpec productSpec = productSpecRepository.findById(receiveOrdersDetailDTO.getSpecId()).orElse(null);
        ordersDetail.setOrders(orders);
        ordersDetail.setProductSpec(productSpec);
        ordersDetail.setQuantity(receiveOrdersDetailDTO.getQuantity());
        ordersDetail.setPrice(receiveOrdersDetailDTO.getPrice());
        ordersDetailRepository.save(ordersDetail);
        return "新增訂單明細成功";
    }

    public String insertOrders(ReceiveOrdersDTO receiveOrdersDTO) {
        Orders orders = new Orders();
        User user = usersRepository.findById(receiveOrdersDTO.getUserId()).orElse(null);
        orders.setUser(user);
        BeanUtils.copyProperties(receiveOrdersDTO, orders);
        ordersRepository.save(orders);
        return "新增訂單成功";
    }
    public String insertOrdersCart(ReceiveOrdersDTO receiveOrdersDTO) {
        Orders orders = new Orders();
        User user = usersRepository.findById(receiveOrdersDTO.getUserId()).orElse(null);
        orders.setUser(user);
        BeanUtils.copyProperties(receiveOrdersDTO, orders);
        orders = ordersRepository.save(orders);
        System.out.println("Order saved with ID: " + orders.getOrderId()); // Log to check the orderId
        return orders.getOrderId().toString();
    }
    public Orders findOrderById(Integer orderId) {
        Optional<Orders> option = ordersRepository.findById(orderId);
        if (option.isPresent()) {
            return option.get();
        }
        return null;
    }


    public Orders updateOrderByOrderId(Integer orderId, ReceiveOrdersDTO receiveOrdersDTO) {
        Orders existingOrders = this.findOrderById(orderId);
        System.out.println(existingOrders);
        BeanUtils.copyProperties(receiveOrdersDTO, existingOrders);
        return ordersRepository.save(existingOrders);
    }


    public String updateOrdersDetailByOrdersDetailId(Integer ordersDetailId, ReceiveOrdersDetailDTO receiveOrdersDetailDTO) {
        OrdersDetail existingOrdersDetail = ordersDetailRepository.findById(ordersDetailId).orElse(null);
        System.out.println(existingOrdersDetail);
//        BeanUtils.copyProperties(receiveOrdersDetailDTO, existingOrdersDetail);
        if (existingOrdersDetail != null) {
            ProductSpec productSpec = productSpecRepository.findById(receiveOrdersDetailDTO.getSpecId()).orElse(null);
            existingOrdersDetail.setProductSpec(productSpec);
            existingOrdersDetail.setQuantity(receiveOrdersDetailDTO.getQuantity());
            existingOrdersDetail.setPrice(receiveOrdersDetailDTO.getPrice());
            ordersDetailRepository.save(existingOrdersDetail);
            return "修改訂單明細成功";
        } else { return null; }
    }

    public void deleteOrdersDetailByOrdersDetailId(Integer ordersDetailId) {
        ordersDetailRepository.deleteById(ordersDetailId);
    }

    public Orders updateOrderStatusByOrderId(Integer orderId, String orderStatus) {
        Orders order = ordersRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setOrderStatus(orderStatus);
            return ordersRepository.save(order);
        } else { return null; }
    }

}

