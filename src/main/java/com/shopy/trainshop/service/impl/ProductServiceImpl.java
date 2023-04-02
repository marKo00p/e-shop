package com.shopy.trainshop.service.impl;

import com.shopy.trainshop.dao.ProductRepository;
import com.shopy.trainshop.domain.Product;
import com.shopy.trainshop.dto.ProductDTO;
import com.shopy.trainshop.service.ProductService;
import com.shopy.trainshop.service.UserService;
import com.shopy.trainshop.utils.ImageUpload;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ImageUpload imageUpload;


    public ProductServiceImpl(ProductRepository productRepository, UserService userService, ImageUpload imageUpload) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.imageUpload = imageUpload;
    }

    @Override
    public List<ProductDTO> getAll() {
        return productRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public List<Product> filterHighPrice() {
        return productRepository.filterHighPrice();
    }

    @Override
    public List<Product> filterLowPrice() {
        return productRepository.filterLowPrice();
    }
    @Override
    public List<Product> findByKeyword(String keyword){
        return productRepository.findByKeyword(keyword);
    }

    @Override
    public boolean saveProduct(MultipartFile file, String title, BigDecimal price, String description, Integer quantity) {
        if(file == null && title==null && price==null && description==null && quantity==null){
            throw new IllegalArgumentException("It's impossible to save the product");
        }
    Product product = new Product();
        try{
        if(imageUpload.uploadImage(file)){
            product.setImage((Base64.getEncoder().encodeToString(file.getBytes())));
        }
            product.setTitle(title);
            product.setPrice(price);
            product.setDescription(description);
            product.setQuantity(quantity);
            productRepository.save(product);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
}

    @Override
    public void deleteById(Long productId) {
         productRepository
                .deleteById(productId);
    }
    @Override
    public boolean updateProduct(MultipartFile file, Long id, String title, BigDecimal price, String description, Integer quantity) {
        if (file == null && title == null && price == null && description == null && quantity == null) {
            throw new IllegalArgumentException("It's impossible to save the product");
        }
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                System.out.println("not a valid file");
            }
            Product updatedProductDTO = productRepository.findProductById(id);
            updatedProductDTO.setTitle(title);
            updatedProductDTO.setDescription(description);
            updatedProductDTO.setPrice(price);
            updatedProductDTO.setQuantity(quantity);
            updatedProductDTO.setImage((Base64.getEncoder().encodeToString(file.getBytes())));
            productRepository.save(updatedProductDTO);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .image(product.getImage())
                .build();
    }

}
