package com.jide.accountservice.usecases.impl;

import com.jide.accountservice.domain.dao.FintechAccountEntityDao;
import com.jide.accountservice.domain.dao.UserDao;
import com.jide.accountservice.domain.entities.FintechAccountEntity;
import com.jide.accountservice.domain.entities.User;
import com.jide.accountservice.domain.enums.AccountOpeningStageConstant;
import com.jide.accountservice.domain.enums.GenderTypeConstant;
import com.jide.accountservice.infrastructure.exceptions.CustomException;
import com.jide.accountservice.usecases.UpdateUserDetailsUseCase;
import com.jide.accountservice.usecases.dto.request.UpdateUserDetailsRequest;
import com.jide.accountservice.usecases.dto.response.AccountCreationResponse;
import com.jide.accountservice.usecases.dto.response.UserDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Named
@AllArgsConstructor
public class UpdateUserDetailsUseCaseImpl implements UpdateUserDetailsUseCase {

    private final UserDao userDao;
    private FintechAccountEntityDao fintechAccountEntityDao;

    private static final Logger logger = LoggerFactory.getLogger(UpdateUserDetailsUseCaseImpl.class);

    @Override
    public UserDetailsResponse updateUserDetails(UpdateUserDetailsRequest updateUserDetailsRequest, String userEmail) {
        User user = userDao.findUserByEmail(userEmail).orElseThrow(() ->  new CustomException("Error: User is not found.", HttpStatus.NOT_FOUND));
        if(!user.getStatus().name().equalsIgnoreCase(AccountOpeningStageConstant.VERIFIED.name())){
            throw new CustomException("Please verify your profile " , HttpStatus.BAD_REQUEST);
        }

        user.setFirstName(updateUserDetailsRequest.getFirstName());
        user.setLastName(updateUserDetailsRequest.getLastName());
        user.setPhoneNumber(updateUserDetailsRequest.getPhoneNumber());
        user.setGender(GenderTypeConstant.valueOf(updateUserDetailsRequest.getGender().toUpperCase()));
        User updatedUser = userDao.saveRecord(user);

        List<FintechAccountEntity> fintechAccountEntityList = fintechAccountEntityDao.getAllAccountsByUser(updatedUser);
        List<AccountCreationResponse> accountCreationResponses = new ArrayList<>();
        if(fintechAccountEntityList.size() > 0) {
            for (FintechAccountEntity fintechAccountEntity : fintechAccountEntityList) {
                AccountCreationResponse accountCreationResponse = AccountCreationResponse.builder()
                        .accountType(fintechAccountEntity.getAccountType().name())
                        .accountNo(fintechAccountEntity.getAccountId())
                        .availableBalance(fintechAccountEntity.getAvailableBalance().toString())
                        .limit(fintechAccountEntity.getDailyTransactionLimit().toString())
                        .build();
                accountCreationResponses.add(accountCreationResponse);
            }
        }

        UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder()
                .dateOfBirth(updatedUser.getDateOfBirth())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .gender(updatedUser.getGender().name())
                .username(updatedUser.getEmail())
                .phoneNumber(updatedUser.getPhoneNumber())
                .accounts(accountCreationResponses).
                        build();
        return userDetailsResponse;
    }
}
