package com.jide.notificationservice.infrastructure.persistence.repository;

import com.jide.notificationservice.domain.entities.Role;
import com.jide.notificationservice.domain.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
