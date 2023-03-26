package com.shopy.trainshop.dao;

import com.shopy.trainshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findFirstByName(String name);
    User findFirstByEmail(String email);
}
