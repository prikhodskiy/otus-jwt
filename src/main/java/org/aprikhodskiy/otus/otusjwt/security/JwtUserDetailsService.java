package org.aprikhodskiy.otus.otusjwt.security;

import lombok.extern.slf4j.Slf4j;
import org.aprikhodskiy.otus.otusjwt.model.User;
import org.aprikhodskiy.otus.otusjwt.repository.UserRepository;
import org.aprikhodskiy.otus.otusjwt.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("jwtUserDetailsService")
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return JwtUser.fromUser(user);
    }
}
