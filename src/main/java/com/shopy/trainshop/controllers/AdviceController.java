package com.shopy.trainshop.controllers;

import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AdviceController {
    private final BucketService bucketService;
    private final ProductService productService;
    public AdviceController(BucketService bucketService, ProductService productService) {
        this.bucketService = bucketService;
        this.productService = productService;
    }
    @ModelAttribute
    public void populateModel(Model model, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {
            model.addAttribute("buckets", new Bucket());
        } else {
            Bucket bucket = bucketService.getBucketBySessionToken(sessionToken);
            model.addAttribute("buckets", bucket);
        }
    }

}
