package com.jide.accountservice.usecases.impl;

import com.jide.accountservice.domain.dao.FintechAccountEntityDao;
import com.jide.accountservice.domain.dao.UserDao;
import com.jide.accountservice.domain.entities.FintechAccountEntity;
import com.jide.accountservice.domain.entities.User;
import com.jide.accountservice.domain.enums.AccountOpeningStageConstant;
import com.jide.accountservice.domain.enums.AccountTypeConstant;
import com.jide.accountservice.domain.enums.GenderTypeConstant;
import com.jide.accountservice.infrastructure.configurations.NotificationFeignClient;
import com.jide.accountservice.infrastructure.exceptions.CustomException;
import com.jide.accountservice.usecases.dto.request.CreateAccountRequest;
import com.jide.accountservice.usecases.dto.request.MicroserviceRequest;
import com.jide.accountservice.usecases.dto.response.AccountCreationResponse;
import com.jide.accountservice.usecases.model.CreateAccountRequestJSON;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreateAccountUseCaseImplTest {
    @Mock
    private UserDao userDao;

    @Mock
    private FintechAccountEntityDao fintechAccountEntityDao;

    @Mock
    private NotificationFeignClient notificationFeignClient;

    @InjectMocks
    private CreateAccountUseCaseImpl createAccountUseCase;

    private static User user;

    private static User userBad;

    private static FintechAccountEntity fintechAccountEntity;
    private static FintechAccountEntity fintechAccountEntityBad;

    private static CreateAccountRequest createAccountRequestBad;
    private static MicroserviceRequest microserviceRequest;
    private static String accountId;

    private static CreateAccountRequest createAccountRequest;

    private static AccountCreationResponse accountCreationResponse;


    @BeforeAll
    public static void init() {


        user = new User();
        user.setId(1L);
        user.setFirstName("john");
        user.setLastName("doe");
        user.setGender(GenderTypeConstant.MALE);
        user.setPhoneNumber("0813705817");
        user.setEmail("j@g.com");
        user.setStatus(AccountOpeningStageConstant.VERIFIED);
        user.setPassword("password");

        userBad = new User();
        userBad.setId(1L);
        userBad.setFirstName("john");
        userBad.setLastName("doe");
        userBad.setGender(GenderTypeConstant.MALE);
        userBad.setPhoneNumber("0813705817");
        userBad.setEmail("j@gg.com");
        userBad.setStatus(AccountOpeningStageConstant.VERIFIED);
        userBad.setPassword("password");

        accountId = "1234567";


        microserviceRequest = new MicroserviceRequest();
        microserviceRequest.setId(user.getId());
        microserviceRequest.setType("ACCOUNT-OPEN");
        microserviceRequest.setUsername(user.getEmail());

        createAccountRequest = new CreateAccountRequest();

        createAccountRequest.setAccountType("SAVINGS");
        createAccountRequest.setLimit(BigDecimal.TEN);

        fintechAccountEntity = new FintechAccountEntity();
        fintechAccountEntity.setAccountId(accountId);
        fintechAccountEntity.setAccountType(AccountTypeConstant.SAVINGS);
        fintechAccountEntity.setCreator(user);
        fintechAccountEntity.setDailyTransactionLimit(createAccountRequest.getLimit());
        fintechAccountEntity.setAvailableBalance(BigDecimal.ZERO);

        accountCreationResponse = new AccountCreationResponse();
        accountCreationResponse.setAccountNo(accountId);
        accountCreationResponse.setAccountType(AccountTypeConstant.SAVINGS.name());
        accountCreationResponse.setLimit(BigDecimal.TEN.toString());
        accountCreationResponse.setAvailableBalance(BigDecimal.ZERO.toString());

    }

    @BeforeEach
    void setMockOutput() {
//        Mockito.when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(fintechAccountEntityDao.generateAccountId()).thenReturn(accountId);
        Mockito.when(fintechAccountEntityDao.saveRecord(fintechAccountEntity)).thenReturn(fintechAccountEntity);
        Mockito.when(notificationFeignClient.notify(microserviceRequest)).thenReturn("sent");



    }

//    @AfterEach
//    void tearDown() {
//    }

    @Test
    public void validateThatMethodDoesNotThrowAnExceptionWithTheRightInput() {
        assertDoesNotThrow(() ->createAccountUseCase.createAccount(createAccountRequest, user.getEmail()));
    }

    @Test
    public void validateThatMethodDoesThrowAnExceptionWithTheWrongInput() {
        Throwable exception = assertThrows(CustomException.class,() ->createAccountUseCase.createAccount(createAccountRequest, userBad.getEmail()));
        assertEquals("Error: User is not found.", exception.getMessage());
    }

    @Test
    public void validateThatMethodRunsWithOutErrors() {
        assertNotNull(createAccountUseCase.createAccount(createAccountRequest, user.getEmail()));

    }

    @Test
    public void validateThatMethodReturnsExpectedResult(){
        System.out.println(accountCreationResponse.toString());
        System.out.println(createAccountUseCase.createAccount(createAccountRequest, user.getEmail()));
        assertThat(createAccountUseCase.createAccount(createAccountRequest, user.getEmail()), is(accountCreationResponse));
    }

}