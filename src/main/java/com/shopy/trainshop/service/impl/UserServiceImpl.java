package com.shopy.trainshop.service.impl;

import com.shopy.trainshop.dao.UserRepository;
import com.shopy.trainshop.domain.Role;
import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.dto.UserDTO;
import com.shopy.trainshop.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final static String USER_EMAIL_NOT_FOUND = "user with email %s not found";

    private  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean saveUser(UserDTO userDTO){
        if(!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())){
            throw new RuntimeException("Password isn't matching");
        }
        User user = User.builder()
                .name(userDTO.getUserName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    public List<User> showAll() {
        return userRepository.findAll();
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    public User saveUserInfo(User user) {
        User user1 = userRepository.findFirstByEmail(user.getEmail());
        user1.setCity(user.getCity());
        user1.setAddress(user.getAddress());
        user1.setZipCode(user.getZipCode());
        user1.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(user1);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
       User user = userRepository.findFirstByEmail(email);
       if(user == null) {
           throw new UsernameNotFoundException(String.format(USER_EMAIL_NOT_FOUND, email));
       }

       List<GrantedAuthority> roles = new ArrayList<>();
       roles.add(new SimpleGrantedAuthority(user.getRole().name()));
       return  new org.springframework.security.core.userdetails.User(
               user.getName(),
               user.getPassword(),
               roles
       );
    }
    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .userName(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


}
