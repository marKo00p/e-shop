package com.shopy.trainshop.service;

import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.domain.Order;
import com.shopy.trainshop.domain.OrderStatus;
import com.shopy.trainshop.dto.ProductDTO;

import java.util.List;

public interface OrderService {
    void saveOrder(Bucket bucket);
    List<Order> getAll();
//    Order saveOrderStatus(Order oder);
    void updateOrderStatus(Long id,OrderStatus orderStatus);

}
