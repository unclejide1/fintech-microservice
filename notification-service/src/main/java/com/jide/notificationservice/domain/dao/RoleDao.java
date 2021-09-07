package com.jide.notificationservice.domain.dao;



import com.jide.notificationservice.domain.entities.Role;
import com.jide.notificationservice.domain.enums.ERole;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
