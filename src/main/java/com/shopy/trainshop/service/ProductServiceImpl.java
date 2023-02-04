package com.shopy.trainshop.service;

import com.shopy.trainshop.dao.ProductRepository;
import com.shopy.trainshop.dto.ProductDTO;
import com.shopy.trainshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }
}
