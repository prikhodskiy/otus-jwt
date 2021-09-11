package org.aprikhodskiy.otus.otusjwt.rest;

import lombok.RequiredArgsConstructor;
import org.aprikhodskiy.otus.otusjwt.dto.UserDto;
import org.aprikhodskiy.otus.otusjwt.model.User;
import org.aprikhodskiy.otus.otusjwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestController {
    private final UserService userService;

    @GetMapping(value = "users/{id}/")
    public ResponseEntity<UserDto> findUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }
}
