package com.jide.notificationservice.infrastructure.persistence.daoImpl;


import com.jide.notificationservice.domain.dao.SequenceEntityDao;
import com.jide.notificationservice.domain.entities.SequenceEntity;
import com.jide.notificationservice.domain.enums.SequenceType;
import com.jide.notificationservice.infrastructure.persistence.repository.SequenceEntityRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.persistence.LockTimeoutException;
import javax.persistence.PessimisticLockException;
import javax.transaction.Transactional;


@Service
public class SequenceEntityDaoImpl implements SequenceEntityDao {

    private final SequenceEntityRepository sequenceEntityRepository;
    public SequenceEntityDaoImpl(SequenceEntityRepository sequenceEntityRepository) {
        this.sequenceEntityRepository = sequenceEntityRepository;
    }

    @Override
    public String getNextAccountId() {
        return String.format("00%010d",  nextId(SequenceType.ACCOUNT_REFERENCE));
    }

    @Override
    public String getNextTransactionReference() {
        return String.format("%s%012d%s", RandomStringUtils.randomNumeric(3),
                nextId(SequenceType.TRANSACTION_CODE), RandomStringUtils.randomNumeric(1));
    }

    @Override
    public String getNextSequenceId(SequenceType sequenceType) {
        return nextId(sequenceType).toString();
    }

    /**
     * Gets the next sequence number of a specific type.
     *
     * @return The next sequence number of a specific type.
     */
    @Retryable(value = { PessimisticLockException.class, LockTimeoutException.class }, maxAttempts = 5, backoff = @Backoff(delay = 1000))
    @Transactional
    public Long nextId(SequenceType sequenceType){
        SequenceEntity appSequenceEntity = sequenceEntityRepository.findFirstBySequenceType(sequenceType)
                .orElseGet(() -> new SequenceEntity(sequenceType));
        Long id = appSequenceEntity.getValue();
        sequenceEntityRepository.saveAndFlush(appSequenceEntity);
        return id;
    }
}
