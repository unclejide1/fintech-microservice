package com.jide.accountservice.infrastructure.web.controllers;

import com.jide.accountservice.Utils;
import com.jide.accountservice.domain.dao.UserDao;
import com.jide.accountservice.domain.entities.Role;
import com.jide.accountservice.domain.entities.User;
import com.jide.accountservice.domain.enums.AccountOpeningStageConstant;
import com.jide.accountservice.domain.enums.ERole;
import com.jide.accountservice.domain.enums.RecordStatusConstant;
import com.jide.accountservice.infrastructure.persistence.repository.UserRepository;
import com.jide.accountservice.usecases.auth.LoginUseCase;
import com.jide.accountservice.usecases.auth.SignUpUseCase;
import com.jide.accountservice.usecases.dto.request.LoginRequest;
import com.jide.accountservice.usecases.model.CreateUserJSON;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc  // Allows the mockmvc to be autowired for us
class AuthControllerTest {

    @Autowired
    private AuthController authController;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SignUpUseCase signUpUseCase;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDao userDao;



    @Autowired
    private LoginUseCase loginUseCase;

    public AuthControllerTest() {
    }


    public User GetLoggedInUser(String email, ERole role) {
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("password"));
        Role role1 = new Role(role);
        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        user.setStatus(AccountOpeningStageConstant.PENDING);
        user.setRoles(roles);
        return user;
    }

    @Test
    public void contextLoads() {
        assertNotNull(authController);
    }


    @Test
    public void SignupWithCorrectValues() {
        Set<String> roles = new HashSet<>();
        roles.add("USER");

        CreateUserJSON userRequest = new CreateUserJSON();
        userRequest.setDateOfBirth("14/11/1990");
        userRequest.setFirstName("john");
        userRequest.setLastName("doe");
        userRequest.setGender("male");
        userRequest.setUsername("doe@jane.com");
        userRequest.setPassword("password");
        userRequest.setRoles(roles);
        userRequest.setPhoneNumber("08137058123");

        try {
            this.mockMvc
                    .perform(
                            post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(userRequest)))
                    // .andDo(print())
                    .andExpect(status().isCreated()).andExpect(content().string(containsString("successfully created a user")));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void SignupWithExisitingValues() {
        String username = "j@g.com";
        when(userRepository.existsByEmailAndRecordStatus(username, RecordStatusConstant.ACTIVE)).thenReturn(true);
        when(userDao.existsByEmail(username)).thenReturn(true);

        Set<String> roles = new HashSet<>();
        roles.add("USER");

        CreateUserJSON userRequest = new CreateUserJSON();
        userRequest.setDateOfBirth("14/11/1990");
        userRequest.setFirstName("john");
        userRequest.setLastName("doe");
        userRequest.setGender("male");
        userRequest.setUsername("j@g.com");
        userRequest.setPassword("password");
        userRequest.setRoles(roles);
        userRequest.setPhoneNumber("08137058124");
        String userRequestJson = Utils.asJsonString(userRequest);

        try {
            this.mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON).content(userRequestJson))
                    // .andDo(print())
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.error", Is.is("A user already exists with this Email: " + username)));
        } catch (Exception e) {
            fail("should not throw an exception with correct details: " + e.getMessage());
        }
    }

    @Test
    public void LoginWithCorrectDetails() {
        User user = GetLoggedInUser("loginuser@mail.com", ERole.ROLE_USER);
        String email = user.getEmail();
        when(userDao.findUserByEmail(email)).thenReturn(Optional.of(user));

        try {
            LoginRequest loginRequest = new LoginRequest(user.getEmail(), "password");
            this.mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(loginRequest)))
                    .andExpect(jsonPath("$.data.token", Is.isA(String.class)))
                    .andExpect(jsonPath("$.data.username", Is.is(user.getEmail())))
                    .andExpect(jsonPath("$.message", Is.is("login successful")));
        } catch (Exception e) {
            fail("should not throw an exception with correct details: " + e.getMessage());
        }
    }
//
    @Test
    public void LoginWithInCorrectDetails() {
        LoginRequest loginRequest = new LoginRequest("doesnotexist@gmail.com", "password");
        try {
            this.mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(loginRequest)))
                    // .andDo(print())
                    .andExpect(status().isUnauthorized());
        } catch (Exception e) {
            // check for the exception here....
            System.out.println(e.getMessage());
            fail("should not throw an exception with correct details");
        }
    }


    @Test
    public void verifyWithCorrectDetails() {
        User user = GetLoggedInUser("loginuser@mail.com", ERole.ROLE_USER);
        when(userDao.getRecordById(user.getId())).thenReturn(user);


        try {
            this.mockMvc.perform(get("/api/v1/auth/{id}", 1))
                    .andExpect(jsonPath("$.data", Is.isA(String.class)))
                    .andExpect(jsonPath("$.message", Is.is("Verification Successful")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("should not throw an exception with correct details: " + e.getMessage());
        }
    }

    @Test
    public void verifyWithIncorrectCorrectDetails() {
        User user = GetLoggedInUser("loginuser@mail.com", ERole.ROLE_USER);
        user.setStatus(AccountOpeningStageConstant.VERIFIED);
        when(userDao.getRecordById(user.getId())).thenReturn(user);


        try {
            this.mockMvc.perform(get("/api/v1/auth/{id}", 1))
                    .andExpect(jsonPath("$.error", Is.is("Profile has already been verified")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("should not throw an exception with correct details: " + e.getMessage());
        }
    }
}