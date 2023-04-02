package com.shopy.trainshop.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopy.trainshop.dao.BucketRepository;
import com.shopy.trainshop.dao.UserRepository;
import com.shopy.trainshop.domain.*;
import com.shopy.trainshop.dto.UserDTO;
import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserController userController;
    @MockBean
    private AdviceController adviceController;
    @MockBean
    private UserService userService;
    @MockBean
    private BucketService bucketService;
    @MockBean
    private BucketRepository bucketRepository;

    BucketItem bucketItem;
    UserDTO userDTOOne;
    UserDTO userDTOTwo;
    User userOne;
    User userTwo;
    Bucket bucket;
    List<User> userList = new ArrayList<>();
    List<UserDTO> userDTOList = new ArrayList<>();
    List<Order> orderList = new ArrayList<>();
    Set<BucketItem> bucketItems = new HashSet<>();
    @BeforeEach
    void setUp() {
        adviceController = new AdviceController(bucketService);
        bucketItem = new BucketItem(1L, 1, new Date(), new Product());
        bucketItems.add(bucketItem);
        bucket = new Bucket(1L,1,new BigDecimal("60"),new Date(),"1223",userOne,bucketItems);
        userOne = new User(1L,"Mike","pass","mike@gmail.com","49000",
                "Dnipro",false,true,"+380501212123","Pravda street",
                Role.CUSTOMER,bucket,orderList);

        userTwo = new User(2L,"Oleg","pass","oleg@gmail.com","49000",
                "Dnipro",false,true,"+380502121321","Titova street",
                Role.CUSTOMER,bucket,orderList);

        userList.add(userOne);
        userList.add(userTwo);

        userDTOOne = UserDTO.builder()
                .userName(userOne.getName())
                .password(userOne.getPassword())
                .matchingPassword(userOne.getPassword())
                .email(userOne.getEmail()).build();
        userDTOTwo = UserDTO.builder()
                .userName(userTwo.getName())
                .password(userTwo.getPassword())
                .matchingPassword(userTwo.getPassword())
                .email(userTwo.getEmail()).build();
        userDTOList.add(userDTOOne);
        userDTOList.add(userDTOTwo);
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void userList()throws Exception {
//        when(userService.getAll()).thenReturn(userDTOList);
//        when(bucketRepository.save(bucket)).thenReturn(bucket);
//        when(bucketService.getBucketBySessionToken("1223")).thenReturn(bucket);
//
//        assertNull(mockMvc.perform(MockMvcRequestBuilders.get("/clients"))
//                .andReturn().getModelAndView());
//
////        mockMvc.perform(MockMvcRequestBuilders.get("/clients")
////                        .accept(MediaType.APPLICATION_JSON)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(toJSON(userDTOOne)))
////
////                .andExpect(status().isOk());
//    }

    private String toJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @Test
    void newUser() {
    }

    @Test
    void saveUser() {
    }
    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .userName(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}