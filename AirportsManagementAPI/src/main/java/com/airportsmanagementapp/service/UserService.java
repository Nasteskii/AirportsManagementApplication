package com.airportsmanagementapp.service;

import com.airportsmanagementapp.model.User;
import com.airportsmanagementapp.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}

