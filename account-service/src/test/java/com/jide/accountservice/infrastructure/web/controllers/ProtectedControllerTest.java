//package com.jide.accountservice.infrastructure.web.controllers;
//
//import com.jide.accountservice.Utils;
//import com.jide.accountservice.infrastructure.security.jwt.JwtUtils;
//import com.jide.accountservice.usecases.model.CreateUserJSON;
//import com.netflix.discovery.converters.Auto;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc// Allows the mockmvc to be autowired for us
//class ProtectedControllerTest {
//
//    @Autowired
//    private ProtectedController protectedController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//
//
//
//    public ProtectedControllerTest() {
//    }
//
//
//    @Test
//    @WithMockUser
//    public void contextLoads() {
//        assertNotNull(protectedController);
//    }
//
//
//    @Test
//    public void SignupWithCorrectValues() {
//
//        Set<String> roles = new HashSet<>();
//        roles.add("USER");
//
//        CreateUserJSON userRequest = new CreateUserJSON();
//        userRequest.setDateOfBirth("14/11/1990");
//        userRequest.setFirstName("john");
//        userRequest.setLastName("doe");
//        userRequest.setGender("male");
//        userRequest.setUsername("doe@jane.com");
//        userRequest.setPassword("password");
//        userRequest.setRoles(roles);
//        userRequest.setPhoneNumber("08137058123");
//
//        String token = String.format("bearer %s",jwtUtils.createToken("doe@jane.com"));
//
//        try {
//            this.mockMvc
//                    .perform(
//                            post("/api/v1/profile/create").header("Authorization", token)).andExpect(status().isOk());
////            .contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(userRequest)))
////                    // .andDo(print())
////                    .andExpect(status().isCreated()).andExpect(content().string(containsString("successfully created a user")));
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
//    }
//
//
//    @Test
//    void createAccount() {
//    }
//
//    @Test
//    void updateProfile() {
//    }
//}