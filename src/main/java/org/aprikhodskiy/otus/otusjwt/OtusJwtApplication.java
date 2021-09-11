package org.aprikhodskiy.otus.otusjwt;

import org.aprikhodskiy.otus.otusjwt.model.Role;
import org.aprikhodskiy.otus.otusjwt.model.User;
import org.aprikhodskiy.otus.otusjwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class OtusJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtusJwtApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            Role userRole = userService.saveRole(Role.builder().name("ROLE_USER").build());
            Role adminRole = userService.saveRole(Role.builder().name("ROLE_ADMIN").build());

            User admin = User.builder()
                    .email("admin@mail.ru")
                    .username("admin")
                    .password("admin")
                    .roles(Arrays.asList(adminRole))
                    .build();
            userService.register(admin);

            User user = User.builder()
                    .email("user@mail.ru")
                    .username("user")
                    .password("user")
                    .roles(Arrays.asList(userRole))
                    .build();
            userService.register(user);

        };
    }
}
