package com.shopy.trainshop.dao;

import com.shopy.trainshop.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BucketRepositoryTest {
    @Autowired
    private BucketRepository bucketRepository;
    Bucket bucket;
    User user;
    Set<BucketItem> bucketItems = new HashSet<>();
    List<Order> orderList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        bucket = new Bucket(1L, 2,new BigDecimal(12), new Date(), "sessionToken", user, bucketItems);
        user = new User(1L,"Mike","pass","mike@gmail.com","49000",
                "Dnipro",false,true,"+380501212123","Pravda street",
                Role.CUSTOMER,bucket,orderList);
        bucketRepository.save(bucket);
    }
    @AfterEach
    void tearDown() {
        bucket = null;
        bucketRepository.deleteAll();
    }
    //Test case "findBySessionToken" SUCCESS
    @Test
    void findBySessionToken() {
         bucket = bucketRepository.findBySessionToken("sessionToken");
        assert(bucket.getSessionToken()).equals(this.user.getBucket().getSessionToken());
    }
    //Test case "findBySessionToken" FAILURE
    @Test
    void findBySessionToken_NotFound() {
        bucket = bucketRepository.findBySessionToken("token");
        assertThat(bucket).isNull();
    }
}