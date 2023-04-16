package com.shopy.trainshop.controllers;

import com.shopy.trainshop.service.OrderService;
import com.shopy.trainshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = MainController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MainController mainController;
    @Mock
    private Model model;
    @MockBean
    private ProductService productService;
    @MockBean
    private OrderService orderService;
    @MockBean
    AdviceController adviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mainController = new MainController(productService, orderService);
    }
    @Test
    void index() {
        when(productService.getAll()).thenReturn(mock(List.class));

        assertEquals("index", mainController.index(model));
        verify(model).addAttribute(eq("products"), any(List.class));
        verify(productService).getAll();
    }
    @Test
    void login() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(view().name("login"));

        assertEquals("login", mainController.login());
    }
    @Test
    void loginError() {
        assertEquals("login", mainController.loginError(model));
        verify(model).addAttribute(eq("loginError"), any(Boolean.class));
    }
    @Test
    void home() throws Exception{
        mockMvc.perform(get("/home"))
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Home")));
        assertEquals("index", mainController.home());
    }
    @Test
    void statistics() {
        when(orderService.getAll()).thenReturn(mock(List.class));
        assertEquals("statistics", mainController.statistics(model));
        verify(model).addAttribute(eq("orders"), any(List.class));
        verify(orderService).getAll();
    }
}