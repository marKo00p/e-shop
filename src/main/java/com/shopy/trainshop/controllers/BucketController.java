package com.shopy.trainshop.controllers;

import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.ProductService;
import com.shopy.trainshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BucketController {
    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }
        @GetMapping("/bucket")
        public String bucket(Model model, HttpServletRequest request){
                return "/bucket";
        }

        @PostMapping("/update-bucket")
        public String updateQuantity(@RequestParam("bucket_item_id") Long id,
                                     @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity) {

            bucketService.updateItemInCart(id, quantity);
            return "redirect:/bucket";
        }

        @GetMapping("/removeBucket/{id}")
        public String removeItemFromBucket(@PathVariable("id") Long id, HttpServletRequest request) {
            String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
            bucketService.removeBucketItemFromBucket(id,sessionToken);
            return "redirect:/bucket";
        }

    @GetMapping("/clearBucket")
    public String clearShoppingBucket(HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        request.getSession(false).removeAttribute("sessionToken");
        bucketService.clearBucket(sessionToken);
        return "redirect:/bucket";
    }


}
