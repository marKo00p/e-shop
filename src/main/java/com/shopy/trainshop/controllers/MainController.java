package com.shopy.trainshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping({"","/"})
    public String index(){
        return "index"; }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
    @RequestMapping("/home")
    public String home(){
        return "index";
    }
    @RequestMapping("/cart")
    public String cart(){
        return "cart";
    }
    @RequestMapping("/catalogue")
    public String catalogue(){
        return "catalogue";
    }
    @RequestMapping("/order")
    public String order(){
        return "order";
    }
    @RequestMapping("/product_card")
    public String productCard(){
        return "product_card";
    }
    @RequestMapping("/services")
    public String service(){
        return "services";
    }
    @RequestMapping("/statistics")
    public String statistics(){
        return "statistics";
    }
}
