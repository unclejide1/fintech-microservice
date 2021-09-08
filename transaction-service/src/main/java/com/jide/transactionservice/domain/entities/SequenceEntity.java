package com.jide.transactionservice.domain.entities;


import com.jide.transactionservice.domain.enums.SequenceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "app_sequence")
public class SequenceEntity {

    public SequenceEntity(SequenceType sequenceType){
        this.sequenceType = sequenceType;
        value = 0;
    }

    public SequenceEntity() {
        value = 0;
    }

    @Version
    private long version; // for optimistic locking

    @Id
    @Enumerated(EnumType.STRING)
    private SequenceType sequenceType;
    private long value;

    /**
     * Gets the current sequence value.
     *
     * @return The current sequence value.
     */
    public Long getValue() {
        return ++value;
    }
}
