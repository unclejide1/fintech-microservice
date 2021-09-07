package com.jide.transactionservice.domain.dao;





import com.jide.transactionservice.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User, Long> {
    User getUserByEmail(String email);
    User getUserByPhoneNumber(String phoneNumber);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    List<User> getAllUsers();
}
