package com.jide.transactionservice.domain.dao;







import com.jide.transactionservice.domain.entities.Role;
import com.jide.transactionservice.domain.enums.ERole;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
