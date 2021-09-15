package org.aprikhodskiy.otus.otusjwt.service;

import org.aprikhodskiy.otus.otusjwt.model.Role;
import org.aprikhodskiy.otus.otusjwt.model.User;

import java.util.List;

public interface UserService {
    User register(User user);
    List<User> getAll();
    User findById(Long id);
    User findByUsername(String username);
    Role saveRole(Role role);
    void delete (Long id);
}
