package com.jide.notificationservice.infrastructure.persistence.daoImpl;





import com.jide.notificationservice.domain.dao.RoleDao;
import com.jide.notificationservice.domain.entities.Role;
import com.jide.notificationservice.domain.enums.ERole;
import com.jide.notificationservice.infrastructure.persistence.repository.RoleRepository;

import javax.inject.Named;
import java.util.Optional;

@Named
public class RoleDaoImpl extends CrudDaoImpl<Role, Long> implements RoleDao {

    private final RoleRepository repository;


    public RoleDaoImpl(RoleRepository repository) {
        super(repository);
        this.repository = repository;
    }
    @Override
    public Optional<Role> findByRole(ERole role) {
        return repository.findByName(role);
    }
}
