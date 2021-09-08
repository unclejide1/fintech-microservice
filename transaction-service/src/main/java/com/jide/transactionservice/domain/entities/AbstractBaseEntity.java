package com.jide.transactionservice.domain.entities;



import com.jide.transactionservice.domain.enums.RecordStatusConstant;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@ToString
@MappedSuperclass
@EnableJpaAuditing
public abstract class AbstractBaseEntity<T extends Serializable> {

    private static final long serialVersionUID = -5554308939380869754L;

    @Id
    @GeneratedValue
    private T id;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime dateModified;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RecordStatusConstant recordStatus = RecordStatusConstant.ACTIVE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntity<?> that = (AbstractBaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (dateCreated == null) {
            dateCreated = LocalDateTime.now();
        }
        dateModified = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
