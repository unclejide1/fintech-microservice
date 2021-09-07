package com.jide.notificationservice.domain.entities;


import com.jide.notificationservice.domain.enums.AccountOpeningStageConstant;
import com.jide.notificationservice.domain.enums.GenderTypeConstant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "appuser")
public class User extends AbstractBaseEntity<Long> {

    @Column(updatable = false,unique = true)
    private String email;


    private String firstName;


    private String lastName;


    @Column(updatable = false)
    private LocalDate dateOfBirth;

    @Column(unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private GenderTypeConstant gender;

    @Enumerated(EnumType.STRING)
    private AccountOpeningStageConstant status;


    @NotBlank(message = "password is required")
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
