package com.jide.accountservice.infrastructure.persistence.repository;

import com.jide.accountservice.domain.entities.FintechAccountEntity;
import com.jide.accountservice.domain.entities.User;
import com.jide.accountservice.domain.enums.RecordStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FintechAccountEntityRepository extends JpaRepository<FintechAccountEntity, Long> {
    Optional<FintechAccountEntity> findFirstByAccountId(String accountId);
    List<FintechAccountEntity> getAllByRecordStatus(RecordStatusConstant statusConstant);
    List<FintechAccountEntity> getAllByCreatorAndRecordStatus(User creator, RecordStatusConstant statusConstant);
}
