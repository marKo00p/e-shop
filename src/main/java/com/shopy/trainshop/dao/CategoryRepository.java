package com.shopy.trainshop.dao;

import com.shopy.trainshop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByTitle(String title);
}
