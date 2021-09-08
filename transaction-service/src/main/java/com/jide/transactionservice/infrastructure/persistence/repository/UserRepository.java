package com.jide.transactionservice.infrastructure.persistence.repository;



import com.jide.transactionservice.domain.entities.User;
import com.jide.transactionservice.domain.enums.RecordStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmailAndRecordStatus(String username, RecordStatusConstant statusConstant);
    Optional<User> findFirstByPhoneNumber(String phoneNumber);
    Boolean existsByEmailAndRecordStatus(String username, RecordStatusConstant statusConstant);
    Boolean existsByPhoneNumberAndRecordStatus(String phoneNumber, RecordStatusConstant statusConstant);
    List<User> getAllByRecordStatus(RecordStatusConstant recordStatusConstant);
}
