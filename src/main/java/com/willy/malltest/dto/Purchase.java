package com.willy.malltest.dto;

import com.willy.malltest.model.Orders;
import com.willy.malltest.model.OrdersDetail;
import com.willy.malltest.model.User;
import lombok.Data;

import java.util.Set;
@Data
public class Purchase {
    private User user;
    private Orders orders;
    private Set<OrdersDetail> orderItems;
}
