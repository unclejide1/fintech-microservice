package com.jide.accountservice.domain.dao;




import com.jide.accountservice.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User, Long> {
    User getUserByEmail(String email);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    Boolean existsByEmail(String email);
    List<User> getAllUsers();
}
