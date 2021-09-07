package com.jide.transactionservice.infrastructure.persistence.daoImpl;








import com.jide.transactionservice.domain.dao.RoleDao;
import com.jide.transactionservice.domain.entities.Role;
import com.jide.transactionservice.domain.enums.ERole;
import com.jide.transactionservice.infrastructure.persistence.repository.RoleRepository;

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
