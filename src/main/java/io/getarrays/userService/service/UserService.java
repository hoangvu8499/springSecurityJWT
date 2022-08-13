package io.getarrays.userService.service;

import io.getarrays.userService.domain.Role;
import io.getarrays.userService.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    User getUser(String userName);
    List<User> getUser();

}
