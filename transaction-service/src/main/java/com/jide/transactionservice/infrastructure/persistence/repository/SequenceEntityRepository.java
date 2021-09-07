package com.jide.transactionservice.infrastructure.persistence.repository;



import com.jide.transactionservice.domain.entities.SequenceEntity;
import com.jide.transactionservice.domain.enums.SequenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Optional;


public interface SequenceEntityRepository extends JpaRepository<SequenceEntity, Long> {

    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "1000")})
    Optional<SequenceEntity> findFirstBySequenceType(SequenceType sequenceType);
}
