package com.shopy.trainshop.controllers;

import com.shopy.trainshop.domain.Order;
import com.shopy.trainshop.domain.OrderStatus;
import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.service.OrderService;
import com.shopy.trainshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    private final ProductService productService;
    private final OrderService orderService;

    public MainController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("products", productService.getAll());
        return "index";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/home")
    public String home() {
        return "index";
    }

    @RequestMapping("/statistics")
    public String statistics(Model model) {
        List<Order> order = orderService.getAll();
        model.addAttribute("orders", order);
        return "statistics";
    }

}
