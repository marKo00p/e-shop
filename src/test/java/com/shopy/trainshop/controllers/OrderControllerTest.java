package com.shopy.trainshop.controllers;

import com.shopy.trainshop.config.MailConfiguration;
import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.domain.Order;
import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.OrderService;
import com.shopy.trainshop.service.UserService;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = BucketController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class OrderControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private Model model;
    @Mock
    private User user;
    @Mock
    private OrderService orderService;
    @Mock
    private RedirectAttributes redirectAttributes;
    @Mock
    private SimpleMailMessage simpleMailMessage;
    @Mock
    private HttpSession httpSession;
    @Mock
    private Principal principal;
    @Mock
    private OrderController orderController;
    @Mock
    private HttpServletRequest request;
    @MockBean
    private BucketService bucketService;
    @MockBean
    AdviceController adviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orderController = new OrderController(bucketService, userService, orderService);
        request.getSession(true);
    }

    @Test
    void userOrder() {
        String sessionToken = "abc123";
        when(request.getSession(true)).thenReturn(httpSession);
        when(httpSession.getAttribute(eq("sessionToken"))).thenReturn(sessionToken);

        when(bucketService.getBucketBySessionToken(eq(sessionToken))).thenReturn(mock(Bucket.class));

        assertEquals("/order", orderController.userOrder(model, request, principal));
        verify(model).addAttribute(eq("user"), any());
        verify(model).addAttribute(eq("bucket"), any());
        verify(bucketService).getBucketBySessionToken(eq(sessionToken));
    }
    @Test
    void userOrder_nullPrincipal() {
        assertEquals("redirect:/login", orderController.userOrder(model, request, null));

    }
    @Test
    void userOrder_nullSession() {
        String sessionToken = "null";
        when(request.getSession(true)).thenReturn(httpSession);
        when(httpSession.getAttribute(eq("sessionToken"))).thenReturn(eq(sessionToken));

        assertEquals("/order", orderController.userOrder(model, request, principal));
        verify(model).addAttribute(eq("bucket"), any());
    }
    @Test
    void updateCustomer() {
        when(userService.saveUserInfo(user)).thenReturn(mock(User.class));
        assertEquals("redirect:/order", orderController.updateCustomer(user, model, redirectAttributes, principal));
        verify(userService).saveUserInfo(user);
        verify(redirectAttributes).addFlashAttribute(eq("user"), any());
    }
    @Test
    void orderDetails_nullPrincipal() {
        assertEquals("redirect:/login", orderController.orderDetails(null, model, request));
    }
//    @Test
//    void orderDetails() {
//        when(principal.getName()).thenReturn("userName");
//        when(userService.findByName(eq("userName"))).thenReturn(mock(User.class));
//        when(user.getBucket()).thenReturn(mock(Bucket.class));
//        doNothing().when(orderService).saveOrder(any());
//        when(javaMailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
//        doNothing().when(javaMailSender).send(simpleMailMessage);
//
//        assertEquals("/text_box", orderController.orderDetails(principal, model, request));
//    }
}