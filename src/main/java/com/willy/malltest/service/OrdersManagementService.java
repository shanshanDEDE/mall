package com.willy.malltest.service;

import com.willy.malltest.dto.OrdersDetailDTO;
import com.willy.malltest.dto.ReceiveOrdersAndDetailDTO;
import com.willy.malltest.dto.ReceiveOrdersDTO;
import com.willy.malltest.dto.ReceiveOrdersDetailDTO;
import com.willy.malltest.model.*;
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

//    public Orders insert(Orders orders) {
//        return ordersRepo.save(orders);
//    }

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


    //此方法的功能是將舊訂單更改狀態為"已取消"，並拿舊訂單來生成一筆內容一樣的新訂單(僅Id及狀態和舊訂單不同)
//    public Orders reorderByOrderId(Integer orderId) {
//        Orders oldOrder = this.findOrderById(orderId);//先透過orderId找到舊訂單
//
//        //換貨前，將舊訂單的狀態更改為已取消
//        oldOrder.setOrderStatus("已取消");
//        Orders updatedOrder = ordersRepository.save(oldOrder);
//
//        //拿原訂單來產生一筆內容一樣的新訂單(僅Id和orderStatus不同)
//        Orders newOrder = new Orders();
//        newOrder.setUser(oldOrder.getUser());
//        newOrder.setOrderDate(oldOrder.getOrderDate());
//        newOrder.setPaymentMethod(oldOrder.getPaymentMethod());
//        newOrder.setDeliverDate(oldOrder.getDeliverDate());
//        newOrder.setPickupDate(oldOrder.getPickupDate());
//        newOrder.setDeliverAddress(oldOrder.getDeliverAddress());
//        newOrder.setRecipientName(oldOrder.getRecipientName());
//        newOrder.setRecipientPhone(oldOrder.getRecipientPhone());
//        newOrder.setPaymentTime(oldOrder.getPaymentTime());
////        newOrder.setOrdersDetails(oldOrder.getOrdersDetails());
//
//        newOrder.setOrderStatus("退換貨重下單");//設置新訂單狀態為"退換貨重下單"
//        return ordersRepository.save(newOrder);
//    }

//    public Integer updateNameById(String recipientName, Integer orderId) {
//        return ordersRepository.updateNameById(recipientName, orderId);
//    }

    //    public Orders insert(ReceiveOrdersDto receiveOrdersDto) {
//        Orders o = new Orders();
//        List<OrdersDetail> od = new ArrayList<OrdersDetail>();
//        o.setOrdersDetails(od);
//        for()
//        BeanUtils.copyProperties(receiveOrdersDto, o);
//        BeanUtils.copyProperties(receiveOrdersDto, od);
//        return ordersRepo.save(o);

    public void deleteOrdersDetailByOrdersDetailId(Integer ordersDetailId) {
        ordersDetailRepository.deleteById(ordersDetailId);
    }

//    public Orders updateOrderStatusByOrderIdXX(Integer orderId) {
//        // 從資料庫中查找訂單
//        Orders order = ordersRepository.findById(orderId).orElse(null);
//        // 如果找到了訂單
//        if (order != null) {
//            String orderStatus = order.getOrderStatus();
//            if (!"已取消".equals(orderStatus)) { // 使用.equals()比較字串
//                order.setOrderStatus("已取消");
//            }
//        // 更新訂單狀態到資料庫
//            return ordersRepository.save(order);
//        } else { return null; }
//    }

    //此方法的功能在於當前端orderStatus下拉選單的選擇改變時，在此接收改變後的值，並存入資料庫
    public Orders updateOrderStatusByOrderId(Integer orderId, String orderStatus) {
        Orders order = ordersRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setOrderStatus(orderStatus);
            return ordersRepository.save(order);
        } else { return null; }
    }

}

