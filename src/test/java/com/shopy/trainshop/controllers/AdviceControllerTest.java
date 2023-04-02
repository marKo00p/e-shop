package com.shopy.trainshop.controllers;

import com.shopy.trainshop.dao.BucketRepository;
import com.shopy.trainshop.domain.BucketItem;
import com.shopy.trainshop.service.BucketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(controllers = AdviceController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class AdviceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BucketRepository bucketRepository;
    @MockBean
    private BucketService bucketService;
    BucketItem bucketItem;

    @Test
    void populateModel() {

    }
}