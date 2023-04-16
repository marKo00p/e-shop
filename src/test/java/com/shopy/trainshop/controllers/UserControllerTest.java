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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdviceController adviceController;
    @MockBean
    private UserController userController;
    @MockBean
    private UserService userService;
    @MockBean
    private Model model;
    @MockBean
    private UserDTO userDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
        userDTO = new UserDTO();
    }
    @Test
    void userList() {
        when(userService.showAll()).thenReturn(mock(List.class));

        assertEquals("clients", userController.userList(model));
        verify(model).addAttribute(eq("users"), any(List.class));
        verify(userService).showAll();
    }

    @Test
    void newUser() {
        assertEquals("sign_up", userController.newUser(model));
        verify(model).addAttribute(eq("user"), any(UserDTO.class));
    }

    @Test
    void saveUser() {
        when(userService.saveUser(userDTO)).thenReturn(true);
        assertEquals("redirect:/", userController.saveUser(userDTO, model));

        verify(userService).saveUser(userDTO);
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(model);
    }

}