package com.shopy.trainshop.controllers;

import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.domain.BucketItem;
import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.OrderService;
import com.shopy.trainshop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = BucketController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class BucketControllerTest {
    @Mock
    private BucketController bucketController;
    @Mock
    private Model model;
    @Mock
    private HttpSession httpSession;
    @Mock
    private HttpServletRequest request;
    @MockBean
    private BucketService bucketService;
    @MockBean
    AdviceController adviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        bucketController = new BucketController(bucketService);
        request.getSession(true);
    }
    @Test
    void bucket() {
        assertEquals("/bucket", bucketController.bucket(model, request));
    }
    @Test
    void updateQuantity() {
        when(bucketService.updateItemInCart(anyLong(), anyInt())).thenReturn(mock(BucketItem.class));

        assertEquals("redirect:/bucket", bucketController.updateQuantity(anyLong(), anyInt()));
        verify(bucketService).updateItemInCart(anyLong(), anyInt());
    }
    @Test
    void removeItemFromBucket() {
        String sessionToken = "abc123";
        when(request.getSession(false)).thenReturn(httpSession);
        when(httpSession.getAttribute(eq("sessionToken"))).thenReturn(sessionToken);
        when(bucketService.removeBucketItemFromBucket(anyLong(), eq(sessionToken))).thenReturn(mock(Bucket.class));

        assertEquals("redirect:/bucket", bucketController.removeItemFromBucket(anyLong(), request));
        verify(bucketService).removeBucketItemFromBucket(anyLong(), eq(sessionToken));
    }
    @Test
    void clearShoppingBucket() {
        String sessionToken = "abc123";
        when(request.getSession(false)).thenReturn(httpSession);
        when(httpSession.getAttribute(eq("sessionToken"))).thenReturn(sessionToken);

        assertEquals("redirect:/bucket", bucketController.clearShoppingBucket(request));
        verify(bucketService).clearBucket(eq(sessionToken));
    }
}