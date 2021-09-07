package com.jide.notificationservice.domain.entities;


import com.jide.notificationservice.domain.enums.ERole;
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
