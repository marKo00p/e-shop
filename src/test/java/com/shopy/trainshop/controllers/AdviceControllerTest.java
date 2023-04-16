package com.shopy.trainshop.controllers;

import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.service.BucketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = AdviceController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class AdviceControllerTest {
    @MockBean
    private BucketService bucketService;
    @MockBean
    private AdviceController adviceController;
    @Mock
    private Model model;
    @Mock
    private HttpServletRequest request;
    Bucket bucket;



    @Test
    void populateModel_WithSessionToken() {
        adviceController = new AdviceController(bucketService);
        bucket = new Bucket();

        String sessionToken = "sessionToken";

        when(request.getSession(true)).thenReturn(mock(HttpSession.class));
        when(request.getSession(true).getAttribute(anyString())).thenReturn(sessionToken);
        when(bucketService.getBucketBySessionToken(sessionToken)).thenReturn(bucket);

        adviceController.populateModel(model, request);

        verify(model).addAttribute("buckets", bucket);
    }

    @Test
    void populateModel_WithoutSessionToken() {
        mock(Bucket.class);
        MockitoAnnotations.initMocks(this);
        adviceController = new AdviceController(bucketService);

        when(request.getSession(true)).thenReturn(mock(HttpSession.class));
        when(request.getSession(true).getAttribute("sessionToken")).thenReturn(null);

        adviceController.populateModel(model, request);

        verify(model).addAttribute(eq("buckets"), any(Bucket.class));
        verifyNoInteractions(bucketService);
    }


}