package com.shopy.trainshop.service;

import com.shopy.trainshop.dao.UserRepository;
import com.shopy.trainshop.domain.Role;
import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final static String USER_EMAIL_NOT_FOUND = "user with email %s not found";
    private final static String USER_NAME_NOT_FOUND = "user with name %s not found";
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
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException{
       User user = userRepository.findFirstByName(name)
               .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NAME_NOT_FOUND, name)));
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
