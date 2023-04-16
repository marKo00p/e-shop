package com.shopy.trainshop.service.impl;

import com.shopy.trainshop.dao.UserRepository;
import com.shopy.trainshop.domain.Bucket;
import com.shopy.trainshop.domain.Order;
import com.shopy.trainshop.domain.Role;
import com.shopy.trainshop.domain.User;
import com.shopy.trainshop.dto.UserDTO;
import com.shopy.trainshop.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;
    @MockBean
    private  PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    AutoCloseable autoCloseable;
    User user;
    Bucket bucket;
    UserDTO userDTO;

    List<Order> orderList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks((this));

        userService = new UserServiceImpl(userRepository, passwordEncoder);

        user = new User(1L,"Mike","pass","mike@gmail.com","49000",
                "Dnipro",false,true,"+380501212123","Pravda street",
                Role.CUSTOMER,bucket,orderList);
        String password = passwordEncoder.encode((user.getPassword()));

        userDTO = UserDTO.builder()
                .userName(user.getName())
                .password(password)
                .matchingPassword(password)
                .email(user.getEmail()).build();
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void saveUser() {
        mock(User.class);
        mock(UserDTO.class);
        mock(UserRepository.class);
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.saveUser(userDTO)).isEqualTo(true);
    }

    @Test
    void getAll() {
        mock(User.class);
        mock(UserDTO.class);
        mock(UserRepository.class);
        when(userRepository.save(user)).thenReturn(user);
        List<User> userDTOList = userRepository.findAll();
        assertThat(userService.getAll()).isEqualTo(userDTOList.stream().map(this::toDto).collect(Collectors.toList()));
    }
    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .userName(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


    @Test
    void findByEmail() {
        mock(User.class);
        mock(UserRepository.class);
        when(userRepository.findFirstByEmail("mike@gmail.com")).thenReturn(user);
        assertThat(userService.findByEmail(user.getEmail())).isEqualTo(user);
    }

    @Test
    void findByName() {
        mock(User.class);
        mock(UserRepository.class);
        when(userRepository.findFirstByName("Mike")).thenReturn(user);
        assertThat(userService.findByName(user.getName())).isEqualTo(user);

    }

    @Test
    void saveUserInfo() {
        mock(User.class);
        mock(UserRepository.class);
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findFirstByEmail("mike@gmail.com")).thenReturn(user);
        assertThat(userService.saveUserInfo(user)).isEqualTo(user);
    }
    @Test
    void loadUserByUsername() {
    }
}