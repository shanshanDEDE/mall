package com.willy.malltest.repository;

import com.willy.malltest.model.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {

//    @Query(value = "select * from orders_detail where order_id = :orderId",nativeQuery = true)
//    List<OrdersDetail> findByOrderId(@Param(value = "orderId") Integer orderId);
}
