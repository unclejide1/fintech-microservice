package com.jide.accountservice.usecases.auth.impl;



import com.google.gson.Gson;
import com.jide.accountservice.domain.dao.RoleDao;
import com.jide.accountservice.domain.dao.UserDao;
import com.jide.accountservice.domain.entities.Role;
import com.jide.accountservice.domain.entities.User;
import com.jide.accountservice.domain.enums.AccountOpeningStageConstant;
import com.jide.accountservice.domain.enums.ERole;
import com.jide.accountservice.domain.enums.GenderTypeConstant;
import com.jide.accountservice.infrastructure.configurations.NotificationFeignClient;
import com.jide.accountservice.infrastructure.exceptions.CustomException;
//import com.jide.accountservice.infrastructure.security.jwt.JwtAuthenticationFilter;
import com.jide.accountservice.usecases.auth.SignUpUseCase;
import com.jide.accountservice.usecases.dto.request.MicroserviceRequest;
import com.jide.accountservice.usecases.dto.request.SignUpRequest;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Named
@AllArgsConstructor
public class SignUpUseCaseImpl implements SignUpUseCase {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final RestTemplate restTemplate;
    private final PasswordEncoder encoder;
    private final NotificationFeignClient notificationFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(SignUpUseCaseImpl.class);



    @Override
    public String createUser(SignUpRequest signUpRequest) {
        if (userDao.existsByEmail(signUpRequest.getEmail())) {
           throw new CustomException("A user already exists with this Email: " + signUpRequest.getEmail(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (userDao.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            throw new CustomException("A user already exists with this Phone number: " + signUpRequest.getPhoneNumber(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(signUpRequest.getDateOfBirth() != null){
            long age = LocalDate.now().getYear() - signUpRequest.getDateOfBirth().getYear();
            if(age < 18){
                throw  new CustomException("Must be above 18 to register on this platform", HttpStatus.BAD_REQUEST);
            }
        }
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleDao.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            addRoles(strRoles, roles, roleDao);
        }


        // Create new user's account
        User user = User.builder().email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .dateOfBirth(signUpRequest.getDateOfBirth())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .gender(GenderTypeConstant.valueOf(signUpRequest.getGender().toUpperCase()))
                .status(AccountOpeningStageConstant.PENDING)
                .build();

        user.setRoles(roles);


        User savedUser =userDao.saveRecord(user);
                logger.info("saved user>>>" + savedUser.toString());
        MicroserviceRequest microserviceRequest = MicroserviceRequest.builder()
                .id(user.getId())
                .username(user.getEmail())
                .type("VERIFY")
                .build();

        String response = "";
        logger.info("about to call notification service");
        try {
            response = notificationFeignClient.notify(microserviceRequest);
        }catch (FeignException.FeignClientException e){
            logger.error(e.getMessage());
        }
        logger.info("called notification service with response "+ response);



//        System.out.println(savedUser.toString());


        return "Saved";
    }

    @Override
    public String verifyUser(Long id) {
            User user = userDao.getRecordById(id);
            if(user.getStatus().name().equalsIgnoreCase(AccountOpeningStageConstant.VERIFIED.name())){
                throw new CustomException("Profile has already been verified", HttpStatus.BAD_REQUEST);
            }
            user.setStatus(AccountOpeningStageConstant.VERIFIED);
            userDao.saveRecord(user);

        return "Profile has been verified";
    }

    public static void addRoles(Set<String> strRoles, Set<Role> roles, RoleDao roleDao) {
        strRoles.forEach(role -> {
            System.out.println(role);
            if ("ADMIN".equalsIgnoreCase(role)) {
                Role adminRole = roleDao.findByRole(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new CustomException("Error: Role is not found.", HttpStatus.BAD_REQUEST));
                roles.add(adminRole);
            } else if("MANAGER".equalsIgnoreCase(role)){
                Role managerRole = roleDao.findByRole(ERole.ROLE_MANAGER)
                        .orElseThrow(() -> new CustomException("Error: Role is not found.", HttpStatus.BAD_REQUEST));
                roles.add(managerRole);
            } else {
                Role userRole = roleDao.findByRole(ERole.ROLE_USER)
                        .orElseThrow(() -> new CustomException("Error: Role is not found.", HttpStatus.BAD_REQUEST));
                roles.add(userRole);
            }
        });
    }
}

