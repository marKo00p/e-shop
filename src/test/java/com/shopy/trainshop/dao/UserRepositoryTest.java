package com.shopy.trainshop.dao;

import com.shopy.trainshop.dao.UserRepository;
import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.domain.Order;
import com.shopy.trainshop.domain.Role;
import com.shopy.trainshop.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    User user;
    Bucket bucket;
    List<Order> orderList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        user = new User(1L,"Mike","pass","mike@gmail.com","49000",
                "Dnipro",false,true,"+380501212123","Pravda street",
                Role.CUSTOMER,bucket,orderList);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        user = null;
        userRepository.deleteAll();
    }
    //Test case "findFirstByName" SUCCESS

    @Test
    void testFindFirstByName_Found(){
        User user = userRepository.findFirstByName("Mike");
        assert(user.getName()).equals(this.user.getName());
    }

    //Test case "findFirstByName" FAILURE
    @Test
    void testFindFirstByName_NotFound(){
        User user = userRepository.findFirstByName("TTT");
        assertThat(user).isNull();
    }
    //Test case "findFirstByEmail" SUCCESS
    @Test
    void findFirstByEmail_Found(){
        User user = userRepository.findFirstByEmail("mike@gmail.com");
        assert(user.getEmail()).equals(this.user.getEmail());
    }
    //Test case "findFirstByEmail" FAILURE
    @Test
    void findFirstByEmail_NotFound(){
        User user = userRepository.findFirstByEmail("ttt@gmail.com");
        assertThat(user).isNull();
    }
}
