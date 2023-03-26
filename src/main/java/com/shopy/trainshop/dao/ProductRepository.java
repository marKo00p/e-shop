package com.shopy.trainshop.dao;

import com.shopy.trainshop.domain.Product;
import com.shopy.trainshop.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    Product findProductById(Long id);
    void deleteById(Long id);
    @Query("select p from Product p" + " order by p.price desc")
    List<Product> filterHighPrice();
    @Query("select p from Product p  order by p.price ")
    List<Product> filterLowPrice();
    @Query( "select p from Product p   where  concat( p.title, ' ',p.description, ' ', p.price, ' ')  ilike  %?1%")
    List<Product> findByKeyword(@Param("keyword") String keyword);
}
