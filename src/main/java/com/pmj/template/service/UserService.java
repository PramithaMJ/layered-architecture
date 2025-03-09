package com.pmj.template.service;


import com.pmj.template.dto.UserDto;
import com.pmj.template.dto.request.UserRequest;
import com.pmj.template.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    UserResponse getUserByUsername(String username);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}