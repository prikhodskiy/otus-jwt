package org.aprikhodskiy.otus.otusjwt.rest;

import lombok.RequiredArgsConstructor;
import org.aprikhodskiy.otus.otusjwt.dto.UserDto;
import org.aprikhodskiy.otus.otusjwt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findUsers(){
        List<UserDto> users = userService.getAll().stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }
}
