package com.jide.transactionservice.infrastructure.persistence.daoImpl;



import com.jide.transactionservice.domain.dao.UserDao;
import com.jide.transactionservice.domain.entities.User;
import com.jide.transactionservice.domain.enums.RecordStatusConstant;
import com.jide.transactionservice.infrastructure.exceptions.CustomException;
import com.jide.transactionservice.infrastructure.persistence.repository.UserRepository;
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
    public User getUserByPhoneNumber(String phoneNumber) {
        return findUserByPhoneNumber(phoneNumber).orElseThrow(() -> new CustomException("Not Found. User with PhoneNumber: " + phoneNumber, HttpStatus.NOT_FOUND));
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
    public Boolean existsByPhoneNumber(String phoneNumber) {
        return repository.existsByEmailAndRecordStatus(phoneNumber, RecordStatusConstant.ACTIVE);
    }


    @Override
    public List<User> getAllUsers() {
        return repository.getAllByRecordStatus(RecordStatusConstant.ACTIVE);
    }
}
