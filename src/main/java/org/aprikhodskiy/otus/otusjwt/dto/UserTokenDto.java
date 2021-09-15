package org.aprikhodskiy.otus.otusjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserTokenDto {
    private String username;
    private String token;
}
