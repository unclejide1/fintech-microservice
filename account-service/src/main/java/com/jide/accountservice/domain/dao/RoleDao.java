package com.jide.accountservice.domain.dao;





import com.jide.accountservice.domain.entities.Role;
import com.jide.accountservice.domain.enums.ERole;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
