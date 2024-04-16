package com.willy.malltest.repository;

import com.willy.malltest.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Transactional
    @Modifying // 若是需要"修改"或"刪除"資料，則需要加@Modifying
    @Query("update Orders o set o.recipientName = :name where o.orderId = :id ")
    Integer updateNameById(@Param("name") String recipientName, @Param("id") Integer orderId);

}
