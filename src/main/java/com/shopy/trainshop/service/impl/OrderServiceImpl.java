package com.shopy.trainshop.service.impl;

import com.shopy.trainshop.dao.BucketItemRepository;
import com.shopy.trainshop.dao.BucketRepository;
import com.shopy.trainshop.dao.OrderDetailsRepository;
import com.shopy.trainshop.dao.OrderRepository;
import com.shopy.trainshop.domain.*;
import com.shopy.trainshop.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final BucketRepository bucketRepository;
    private final BucketItemRepository bucketItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository, BucketRepository bucketRepository, BucketItemRepository bucketItemRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.bucketRepository = bucketRepository;
        this.bucketItemRepository = bucketItemRepository;
    }


    @Override
    public void saveOrder(Bucket bucket) {
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(bucket.getUser());
        order.setTotalPrice(bucket.getTotalPrice());
        order.setAddress(orderAddress(bucket.getUser()));
        orderRepository.save(order);


        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (BucketItem item : bucket.getBucketItems()) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setItemPrice(item.getProduct().getPrice());
            orderDetails.setTotalPrice(order.getTotalPrice());
            orderDetails.setQuantity(item.getQuantity());
            orderDetails.setProduct(item.getProduct());
            orderDetailsRepository.save(orderDetails);
            orderDetailsList.add(orderDetails);
            bucketItemRepository.delete(item);

        }
        order.setDetails(orderDetailsList);
        bucket.setBucketItems(new HashSet<>());
        bucket.setTotalItems(0);
        bucket.setTotalPrice(BigDecimal.valueOf(0));
        bucketRepository.save(bucket);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public String orderAddress(User user) {
        List<String> fullAddress = Arrays.asList(user.getZipCode(), user.getCity(), user.getAddress());
        return String.join(" ", fullAddress);
    }

}
