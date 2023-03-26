package com.shopy.trainshop.service;

import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean saveUser(UserDTO userDTO);
//    void saveUser(User user);
    List<UserDTO> getAll();
    List<User> showAll();
    User findByEmail(String name);
    User findByName(String name);
    User saveUserInfo(User user);


}
