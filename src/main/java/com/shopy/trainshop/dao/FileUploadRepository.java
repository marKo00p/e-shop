package com.shopy.trainshop.dao;

import com.shopy.trainshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<Product, Long> {

}
