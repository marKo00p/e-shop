package com.shopy.trainshop.dao;

import com.shopy.trainshop.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository <OrderDetails, Long> {
}
