package com.shopy.trainshop.controllers;

import com.shopy.trainshop.dto.ProductDTO;
import com.shopy.trainshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/catalogue")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public String list(Model model) {
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("catalogue", list);
        return "catalogue";
    }
}
