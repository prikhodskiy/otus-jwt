package org.aprikhodskiy.otus.otusjwt.rest;

import lombok.RequiredArgsConstructor;
import org.aprikhodskiy.otus.otusjwt.dto.AuthenticationRequestDto;
import org.aprikhodskiy.otus.otusjwt.dto.UserTokenDto;
import org.aprikhodskiy.otus.otusjwt.model.User;
import org.aprikhodskiy.otus.otusjwt.security.jwt.JwtTokenProvider;
import org.aprikhodskiy.otus.otusjwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
            );
            User user = userService.findByUsername(requestDto.getUsername());
            if (user == null)
                throw new UsernameNotFoundException("User not found");

            String token = jwtTokenProvider.createToken(requestDto.getUsername(), user.getRoles());
            return ResponseEntity.ok(new UserTokenDto(requestDto.getUsername(), token));

        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials");
        }
    }

    @PostMapping("/api/v1/auth/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
