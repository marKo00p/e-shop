package com.shopy.trainshop.controllers;

import com.shopy.trainshop.domain.Product;
import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.dto.ProductDTO;
import com.shopy.trainshop.service.BucketService;
import com.shopy.trainshop.service.ProductService;
import com.shopy.trainshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final BucketService bucketService;

    public ProductController(ProductService productService, UserService userService, BucketService bucketService) {
        this.productService = productService;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @GetMapping("/catalogue")
    public String list(Model model) {
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "catalogue";
    }
    @GetMapping("/services")
    public String getAll(Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "services";
    }


    @GetMapping("/add_product")
    public String newProduct(Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("product", new ProductDTO());
        return "/add_product";
    }


    @PostMapping("/add_product")
    public String addProduct(@RequestParam("title") String title,
                         @RequestParam("price") BigDecimal price,
                         @RequestParam("description") String description,
                         @RequestParam("quantity") Integer quantity,
                         @RequestParam("image") MultipartFile file,
                         Model model) throws IOException {
       if(productService.saveProduct(file, title, price, description, quantity)){
           return "redirect:/services";
       } else {
           model.addAttribute("product", new ProductDTO());
           return "add_product";
       }
       }

      @GetMapping("/delete_product/{id}")
      public String deleteProduct(@PathVariable("id") Long id, Model model){
          productService.deleteById(id);
          model.addAttribute("product", new ProductDTO());
          return "redirect:/services";
      }

    @GetMapping("/edit_product/{id}")

    public String getOne(@PathVariable("id") Long id, Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        ProductDTO productDTO =productService.getProductById(id);
        model.addAttribute("product", productDTO);
        return "edit_product";
    }

    @PostMapping("/edit_product")
    public String editProduct(@RequestParam("id") Long id,
                              @RequestParam("title") String title,
                              @RequestParam("price") BigDecimal price,
                              @RequestParam("description") String description,
                              @RequestParam("quantity") Integer quantity,
                              @RequestParam("image") MultipartFile file,
                              Model model) {

        if (productService.updateProduct(file, id, title, price, description, quantity)) {
            return "redirect:/services";
        }
        else {
            model.addAttribute("product", new ProductDTO());
            return "services";
       }
    }

    @GetMapping( "/product_card/{id}")
    public String productCardView(@PathVariable("id") Long id, Model model){
        ProductDTO productDTO = productService.getProductById(id);
        model.addAttribute("product", productDTO);
        return "/product_card";
    }

    @PostMapping("/add_product_to_bucket")
    public String addToBucket(@RequestParam("id") Long id,
                              @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity,
                              Model model,
                              Principal principal,
                              HttpServletRequest request) {
        if(principal == null) {
            return "redirect:/login";
        }

        String userName = principal.getName();
        User user = userService.findByName(userName);

        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {

            sessionToken = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionToken", sessionToken);
            bucketService.addItemToCart(id, sessionToken, quantity, user);
        } else {
            bucketService.addToExistingBucket(id, sessionToken, quantity, user);
        }
        return "redirect:/bucket" ;
    }
    @GetMapping("/high-price")
    public String filterHighPrice(Model model){
        List<Product> products = productService.filterHighPrice();
        model.addAttribute("products", products);
        return "/fragments/high-price";
    }


    @GetMapping("/low-price")
    public String filterLowPrice(Model model){
        List<Product> products = productService.filterLowPrice();
        model.addAttribute("products", products);
        return "/fragments/low-price";
    }
    @GetMapping("/products")
    public String getProduct(Model model, String keyword){
        if(keyword != null){
            model.addAttribute("products", productService.findByKeyword(keyword));
        } else {
            model.addAttribute("products", productService.getAll());
        }
        return "/catalogue";
    }
}
