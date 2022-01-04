package com.devcamp.Project.service;

import com.devcamp.Project.dto.UserPrincipal;
import com.devcamp.Project.entity.User;

public interface UserService {
    User createUser(User user);

    UserPrincipal findByUsername(String username);
}
