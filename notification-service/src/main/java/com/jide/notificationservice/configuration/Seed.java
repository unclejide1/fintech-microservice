package com.jide.notificationservice.configuration;


import com.jide.notificationservice.domain.dao.RoleDao;
import com.jide.notificationservice.domain.entities.Role;
import com.jide.notificationservice.domain.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class Seed {


    private final RoleDao roleDao;

    @Autowired
    public Seed(RoleDao roleDao) {
        this.roleDao = roleDao;
    }




    @PostConstruct
    public void Seeder(){
        Optional<Role>  adminRole = roleDao.findByRole(ERole.ROLE_ADMIN);
        Optional<Role>  userRole = roleDao.findByRole(ERole.ROLE_USER);
        Optional<Role>  managerRole = roleDao.findByRole(ERole.ROLE_MANAGER);


        if(adminRole.isEmpty()){
            createRole(ERole.ROLE_ADMIN);
        }
        if(userRole.isEmpty()){
            createRole(ERole.ROLE_USER);
        }
        if(managerRole.isEmpty()){
            createRole(ERole.ROLE_MANAGER);
        }


    }

    public Role createRole(ERole role){
        return roleDao.saveRecord(Role.builder().name(role).build());

    }



}