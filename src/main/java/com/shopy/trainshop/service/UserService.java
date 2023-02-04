package com.shopy.trainshop.service;

import com.shopy.trainshop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean saveUser(UserDTO userDTO);
    List<UserDTO> getAll();

}
