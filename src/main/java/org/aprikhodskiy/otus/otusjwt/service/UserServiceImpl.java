package org.aprikhodskiy.otus.otusjwt.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aprikhodskiy.otus.otusjwt.dto.UserDto;
import org.aprikhodskiy.otus.otusjwt.exceptions.UserNotFoundException;
import org.aprikhodskiy.otus.otusjwt.model.Role;
import org.aprikhodskiy.otus.otusjwt.model.Status;
import org.aprikhodskiy.otus.otusjwt.model.User;
import org.aprikhodskiy.otus.otusjwt.repository.RoleRepository;
import org.aprikhodskiy.otus.otusjwt.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public User register(User user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        List<Role> roleList = new ArrayList<>();
        roleList.add(userRole);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.delete(this.findById(id));
    }

    @Transactional
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
