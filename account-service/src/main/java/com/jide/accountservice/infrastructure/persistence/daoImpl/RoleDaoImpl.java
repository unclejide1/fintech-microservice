package com.jide.accountservice.infrastructure.persistence.daoImpl;






import com.jide.accountservice.domain.dao.RoleDao;
import com.jide.accountservice.domain.entities.Role;
import com.jide.accountservice.domain.enums.ERole;
import com.jide.accountservice.infrastructure.persistence.repository.RoleRepository;

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
