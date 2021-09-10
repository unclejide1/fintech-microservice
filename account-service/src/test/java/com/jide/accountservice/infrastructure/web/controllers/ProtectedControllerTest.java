package com.jide.accountservice.infrastructure.web.controllers;

import com.jide.accountservice.Utils;
import com.jide.accountservice.domain.dao.FintechAccountEntityDao;
import com.jide.accountservice.domain.dao.UserDao;
import com.jide.accountservice.domain.entities.FintechAccountEntity;
import com.jide.accountservice.domain.entities.Role;
import com.jide.accountservice.domain.entities.User;
import com.jide.accountservice.domain.enums.AccountAccessStatusConstant;
import com.jide.accountservice.domain.enums.AccountOpeningStageConstant;
import com.jide.accountservice.domain.enums.AccountTypeConstant;
import com.jide.accountservice.domain.enums.ERole;
import com.jide.accountservice.infrastructure.security.jwt.JwtUtils;
import com.jide.accountservice.infrastructure.security.services.MyUserDetailsService;
import com.jide.accountservice.usecases.dto.request.CreateAccountRequest;
import com.jide.accountservice.usecases.model.CreateAccountRequestJSON;
import com.jide.accountservice.usecases.model.CreateUserJSON;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
//@ContextConfiguration(locations = "classpath:app-context.xml")
@AutoConfigureMockMvc// Allows the mockmvc to be autowired for us
class ProtectedControllerTest {

    @Autowired
    private ProtectedController protectedController;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FintechAccountEntityDao fintechAccountEntityDao;


    @Autowired
    private JwtUtils jwtUtils;




    public ProtectedControllerTest() {
    }


    @Test
    public void contextLoads() {
        assertNotNull(protectedController);
    }


    @Test
    @WithUserDetails(value = "jyddynwoks@yahoo.com")
//    @WithMockUser(username = "doe@jane.com", roles={"USER","ADMIN"})
    public void createAccountWithUnverifiedProfile() {
        CreateAccountRequestJSON createAccountRequestJSON = new CreateAccountRequestJSON();
        createAccountRequestJSON.setAccountType("SAVINGS");
        createAccountRequestJSON.setLimit(BigDecimal.TEN);

        String token = String.format("Bearer %s",jwtUtils.createToken("jyddynwoks@yahoo.com"));

        try {
            this.mockMvc
                    .perform(
                            post("/api/v1/profile/create").header("Authorization", token).contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(createAccountRequestJSON)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


}