package com.jide.accountservice.domain.entities;


import com.jide.accountservice.domain.enums.ERole;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends AbstractBaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
