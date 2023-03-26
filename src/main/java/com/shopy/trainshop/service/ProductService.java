package com.shopy.trainshop.service;

import com.shopy.trainshop.domain.Category;
import com.shopy.trainshop.domain.Product;
import com.shopy.trainshop.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAll();
     ProductDTO getProductById(Long id);
     Product getById(Long id);
    List<Product> filterHighPrice();

    List<Product> filterLowPrice();
    boolean saveProduct(MultipartFile file, String title, BigDecimal price, String description, Integer quantity);
    void deleteById(Long id);
    boolean updateProduct(MultipartFile file,Long id, String title, BigDecimal price, String description, Integer quantity);
    List<Product> findByKeyword(String keyword);



}
