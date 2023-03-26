package com.shopy.trainshop.dao;


import com.shopy.trainshop.domain.BucketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketItemRepository extends JpaRepository<BucketItem, Long> {
}
