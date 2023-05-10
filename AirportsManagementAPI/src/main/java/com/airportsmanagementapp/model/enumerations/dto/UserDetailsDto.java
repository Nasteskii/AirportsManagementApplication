package com.airportsmanagementapp.model.enumerations.dto;

import com.airportsmanagementapp.model.User;
import com.airportsmanagementapp.model.enumerations.Role;
import lombok.Data;

@Data
public class UserDetailsDto {
    private String username;
    private Role role;

    public static UserDetailsDto of(User user) {
        UserDetailsDto details = new UserDetailsDto();
        details.username = user.getUsername();
        details.role = user.getRole();
        return details;
    }
}

