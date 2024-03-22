package com.willy.malltest.repository;


import com.willy.malltest.model.CartItems;
import com.willy.malltest.model.Product;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {

    public CartItems findByUserAndProductSpec(User user, ProductSpec productSpec);

    public List<CartItems> findByUser(User user);
}

