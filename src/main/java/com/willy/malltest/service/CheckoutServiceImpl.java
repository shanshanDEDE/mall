package com.willy.malltest.service;

import com.willy.malltest.dto.Purchase;
import com.willy.malltest.dto.PurchaseResponse;
import com.willy.malltest.model.Orders;
import com.willy.malltest.model.OrdersDetail;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
@Service
public class CheckoutServiceImpl implements CheckoutService{

    private UsersRepository usersRepository;

    public CheckoutServiceImpl(UsersRepository customerRepository) {

        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Orders orders = purchase.getOrders();

        // generate tracking number
//        Integer orderTrackingNumber = generateOrderTrackingNumber();
//        orders.setOrderID(orderTrackingNumber);

        // populate order with orderItems
        Set<OrdersDetail> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> orders.add(item));

        // populate order with shippingAddress
        orders.setDeliverAddress(purchase.getOrders().getDeliverAddress());


        // populate customer with order
        User user = purchase.getUser();
        user.add(orders);

        // save to the database
        usersRepository.save(user);

        // return a response
//        return new PurchaseResponse(orderTrackingNumber);
        return new PurchaseResponse(orders.getOrderID());
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
    }
}
