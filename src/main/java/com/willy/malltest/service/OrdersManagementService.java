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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersManagementService {

    @Autowired
    private OrdersRepository ordersRepo;

    @Autowired
    private OrdersDetailRepository ordersDetailRepo;

    @Autowired
    private UsersRepository usersRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductSpecRepository productSpecRepo;

    public List<Orders> findAll() {
        return ordersRepo.findAll();
    }

    //此方法目的在於呈現所有訂單明細相關資訊
    public List<OrdersDetailDTO> findOrdersDetailDTOs() {
        List<OrdersDetail> ordersDetails = ordersDetailRepo.findAll();
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

//    public Orders insert(Orders orders) {
//        return ordersRepo.save(orders);
//    }

    public String insertOrdersDetail(ReceiveOrdersDetailDTO receiveOrdersDetailDTO) {
        OrdersDetail ordersDetail = new OrdersDetail();
        Orders orders = ordersRepo.findById(receiveOrdersDetailDTO.getOrderId()).orElse(null);
        ProductSpec productSpec = productSpecRepo.findById(receiveOrdersDetailDTO.getSpecId()).orElse(null);
        ordersDetail.setOrders(orders);
        ordersDetail.setProductSpec(productSpec);
        ordersDetail.setQuantity(receiveOrdersDetailDTO.getQuantity());
        ordersDetail.setPrice(receiveOrdersDetailDTO.getPrice());
        ordersDetailRepo.save(ordersDetail);
        return "新增訂單明細成功";
    }

    public String insertOrders(ReceiveOrdersDTO receiveOrdersDTO) {
        Orders orders = new Orders();
        User user = usersRepo.findById(receiveOrdersDTO.getUserId()).orElse(null);
        orders.setUser(user);
        BeanUtils.copyProperties(receiveOrdersDTO, orders);
        orders = ordersRepo.save(orders);
        System.out.println("Order saved with ID: " + orders.getOrderId()); // Log to check the orderId
        return orders.getOrderId().toString();
    }

//    public String insertOrdersAndDetail(ReceiveOrdersAndDetailDTO receiveOrdersAndDetailDTO) {
//        Orders orders = new Orders();
//        User user = usersRepo.findById(receiveOrdersAndDetailDTO.getUserId()).orElse(null);
//        orders.setUser(user);
//        BeanUtils.copyProperties(receiveOrdersAndDetailDTO, orders);
//
//        OrdersDetail ordersDetail = new OrdersDetail();
//        ProductSpec productSpec = productSpecRepo.findById(receiveOrdersAndDetailDTO.getSpecId()).orElse(null);
//        ordersDetail.setProductSpec(productSpec);
//        BeanUtils.copyProperties(receiveOrdersAndDetailDTO, ordersDetail);
//        ordersDetail.setOrders(orders);

//        ordersRepo.save(orders);
//        ordersDetailRepo.save(ordersDetail);
//        return "新增訂單成功";
//    }


    public Orders findOrderById(Integer orderId) {
        Optional<Orders> option = ordersRepo.findById(orderId);
        if (option.isPresent()) {
            return option.get();
        }
        return null;
    }


    public Orders updateOrderByOrderId(Integer orderId, ReceiveOrdersDTO receiveOrdersDTO) {
        Orders existingOrders = this.findOrderById(orderId);
        System.out.println(existingOrders);
        BeanUtils.copyProperties(receiveOrdersDTO, existingOrders);
        return ordersRepo.save(existingOrders);
    }

    //此方法的功能是將舊訂單更改狀態為"已取消"，並拿舊訂單來生成一筆內容一樣的新訂單(僅Id及狀態和舊訂單不同)
    public Orders reorderByOrderId(Integer orderId) {
        Orders oldOrder = this.findOrderById(orderId);//先透過orderId找到舊訂單

        //換貨前，將舊訂單的狀態更改為已取消
        oldOrder.setOrderStatus("已取消");
        Orders updatedOrder = ordersRepo.save(oldOrder);

        //拿原訂單來產生一筆內容一樣的新訂單(僅Id和orderStatus不同)
        Orders newOrder = new Orders();
        newOrder.setUser(oldOrder.getUser());
        newOrder.setOrderDate(oldOrder.getOrderDate());
        newOrder.setPaymentMethod(oldOrder.getPaymentMethod());
        newOrder.setDeliverDate(oldOrder.getDeliverDate());
        newOrder.setPickupDate(oldOrder.getPickupDate());
        newOrder.setDeliverAddress(oldOrder.getDeliverAddress());
        newOrder.setRecipientName(oldOrder.getRecipientName());
        newOrder.setRecipientPhone(oldOrder.getRecipientPhone());
        newOrder.setPaymentTime(oldOrder.getPaymentTime());
//        newOrder.setOrdersDetails(oldOrder.getOrdersDetails());

        newOrder.setOrderStatus("退換貨重下單");//設置新訂單狀態為"退換貨重下單"
        return ordersRepo.save(newOrder);
    }

    public Integer updateNameById(String recipientName, Integer orderId) {
        return ordersRepo.updateNameById(recipientName, orderId);
    }

    //    public Orders insert(ReceiveOrdersDto receiveOrdersDto) {
//        Orders o = new Orders();
//        List<OrdersDetail> od = new ArrayList<OrdersDetail>();
//        o.setOrdersDetails(od);
//        for()
//        BeanUtils.copyProperties(receiveOrdersDto, o);
//        BeanUtils.copyProperties(receiveOrdersDto, od);
//        return ordersRepo.save(o);
    public void updateOrderStatusByOrderId(Integer orderId) {
        // 從資料庫中查找訂單
        Orders orders = ordersRepo.findById(orderId).orElse(null);

        // 如果找到了訂單
        if (orders != null) {
            String orderStatus = orders.getOrderStatus();

            // 檢查訂單狀態是否為 "處理中"
            if ("處理中".equals(orderStatus)) { // 使用.equals()比較字串
                orders.setOrderStatus("已取消");
            } else if ("已取消".equals(orderStatus)) { // 使用.equals()比較字串
                orders.setOrderStatus("處理中");
            }

            // 更新訂單狀態到資料庫
            ordersRepo.save(orders);
        }
    }


}

