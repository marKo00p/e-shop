package com.shopy.trainshop.controllers;

import com.shopy.trainshop.dao.OrderRepository;
import com.shopy.trainshop.dao.ProductRepository;
import com.shopy.trainshop.dao.UserRepository;
import com.shopy.trainshop.dto.ProductDTO;
import com.shopy.trainshop.service.OrderService;
import com.shopy.trainshop.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = MainController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductService productService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private OrderService orderService;
    private final String PATH_TO_IMAGE = "src/main/resources/static/images/eko_bag.jpg";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void index() throws Exception {
//        when(productService.getAll()).thenReturn(Arrays.asList(
//                new ProductDTO(1L,"Bag", new BigDecimal("60"), "mini handbag", 5, PATH_TO_IMAGE),
//                new ProductDTO(2L,"Eko Bag", new BigDecimal("20"), "nice bag", 15, PATH_TO_IMAGE)
//        ));
//        mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                .andDo(print())
////                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1,2)))
//                .andExpect(view().name("index"));
////                .andReturn();
//    }
//
//    @Test
//    void login() throws Exception {
//        this.mockMvc.perform(get("/login"))
//                .andDo(print())
//                .andExpect(view().name("login"));
//    }

    @Test
    void loginError() {
    }

    @Test
    void home() {
    }

    @Test
    void statistics() {
    }
}