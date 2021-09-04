package com.jide.accountservice.infrastructure.persistence.repository;


import com.jide.accountservice.domain.entities.Role;
import com.jide.accountservice.domain.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
