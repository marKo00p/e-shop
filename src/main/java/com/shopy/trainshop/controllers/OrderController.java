package com.shopy.trainshop.controllers;

import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.OrderService;
import com.shopy.trainshop.service.ProductService;
import com.shopy.trainshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.mail.javamail.JavaMailSender;
import java.security.Principal;

@Controller
public class OrderController {
    private final BucketService bucketService;
    private final UserService userService;
    private final OrderService orderService;
    @Autowired
    private JavaMailSender javaMailSender;

    public OrderController(BucketService bucketService, UserService userService, OrderService orderService) {
        this.bucketService = bucketService;
        this.userService = userService;
        this.orderService = orderService;

    }

    @GetMapping("/order")
    public String userOrder(Model model, HttpServletRequest request, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {
            model.addAttribute("bucket", new Bucket());
        } else {
            Bucket bucket = bucketService.getBucketBySessionToken(sessionToken);
            User user = bucket.getUser();

            model.addAttribute("user", user);
            model.addAttribute("bucket", bucket);
        }
        return "/order";
    }

    @PostMapping("/order-info")
    public String updateCustomer(@ModelAttribute("user") User user,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {

        User userSaved = userService.saveUserInfo(user);

        redirectAttributes.addFlashAttribute("user", userSaved);

        return "redirect:/order";
    }

    @GetMapping("/text_box")
    public String orderDetails(Principal principal, Model model, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }
        String userName = principal.getName();
        User user = userService.findByName(userName);
        Bucket bucket = user.getBucket();
        orderService.saveOrder(bucket);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("semykinayuliia@gmail.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Order details");
        mailMessage.setText("Hello! We are so excited to send you [Products Ordered]. Your order will be processed and shipped as soon as possible. An email with tracking information will be sent to you once your order has shipped.\n" +
                "\n" +
                "In the meantime, we would love it if you could follow us on social media or sign up for our newsletter for exclusive deals and promotions.\n" +
                "\n" +
                "Thank you.");

        javaMailSender.send(mailMessage);
        return "/text_box";
        }


}
