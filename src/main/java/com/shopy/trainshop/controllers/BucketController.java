package com.shopy.trainshop.controllers;

import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.domain.BucketItem;
import com.shopy.trainshop.domain.Product;
import com.shopy.trainshop.dto.BucketDTO;
import com.shopy.trainshop.dto.BucketItemDTO;
import com.shopy.trainshop.dto.ProductDTO;
import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.ProductService;
import com.shopy.trainshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.shopy.trainshop.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BucketController {
    private final BucketService bucketService;
    private final ProductService productService;
    private final UserService userService;

    public BucketController(BucketService bucketService, ProductService productService, UserService userService) {
        this.bucketService = bucketService;
        this.productService = productService;
        this.userService = userService;
    }
        @GetMapping("/bucket")
    public String bucket(Model model, HttpServletRequest request){
//            String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
//            if (sessionToken == null) {
//                model.addAttribute("buckets", new Bucket());
//            } else {
//                Bucket bucket = bucketService.getBucketBySessionToken(sessionToken);
//                model.addAttribute("buckets", bucket);
//            }
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
    public String clearShoopiString(HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        request.getSession(false).removeAttribute("sessionToken");
        bucketService.clearBucket(sessionToken);
        return "redirect:/bucket";
    }


}
