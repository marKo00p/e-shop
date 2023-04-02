package com.shopy.trainshop.dao;

import com.shopy.trainshop.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Bucket findBySessionToken(String sessionToken);
}
