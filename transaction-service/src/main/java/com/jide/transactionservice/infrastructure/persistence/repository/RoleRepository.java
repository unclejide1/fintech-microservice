package com.jide.transactionservice.infrastructure.persistence.repository;



import com.jide.transactionservice.domain.entities.Role;
import com.jide.transactionservice.domain.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
