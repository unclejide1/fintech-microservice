package com.jide.notificationservice.infrastructure.persistence.daoImpl;



import com.jide.notificationservice.domain.dao.UserDao;
import com.jide.notificationservice.domain.entities.User;
import com.jide.notificationservice.domain.enums.RecordStatusConstant;
import com.jide.notificationservice.infrastructure.exceptions.CustomException;
import com.jide.notificationservice.infrastructure.persistence.repository.UserRepository;
import org.springframework.http.HttpStatus;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
public class UserDaoImpl extends CrudDaoImpl<User, Long> implements UserDao {
    private final UserRepository repository;


    public UserDaoImpl(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public User getUserByEmail(String email) {
        return findUserByEmail(email).orElseThrow(() -> new CustomException("Not Found. User with Email: " + email, HttpStatus.NOT_FOUND));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return repository.findFirstByEmailAndRecordStatus(email, RecordStatusConstant.ACTIVE);
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return repository.findFirstByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmailAndRecordStatus(email, RecordStatusConstant.ACTIVE);
    }


    @Override
    public List<User> getAllUsers() {
        return repository.getAllByRecordStatus(RecordStatusConstant.ACTIVE);
    }
}
