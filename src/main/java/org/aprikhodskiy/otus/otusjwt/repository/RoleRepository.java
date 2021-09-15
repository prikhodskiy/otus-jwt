package org.aprikhodskiy.otus.otusjwt.repository;

import org.aprikhodskiy.otus.otusjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
