package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.service.model.AccountDto;

import java.util.Optional;

public interface AccountService {

    Optional<AccountDto> signUp(String username, String password);

    Optional<AccountDto> signUpPrivileged(String username, String password);

    Optional<AccountDto> signIn(String username, String password);

    Optional<AccountDto> signInPrivileged(String username, String password);

    Optional<AccountDto> signOut();

    Optional<AccountDto> getSignedInUser();
}
