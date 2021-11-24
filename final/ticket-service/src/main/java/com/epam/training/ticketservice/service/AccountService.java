package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.service.model.AccountDto;
import lombok.NonNull;

import java.util.Optional;

public interface AccountService {

    Optional<AccountDto> signUp(@NonNull String username, @NonNull String password);

    Optional<AccountDto> signUpPrivileged(@NonNull String username, @NonNull String password);

    Optional<AccountDto> signIn(@NonNull String username, @NonNull String password);

    Optional<AccountDto> signInPrivileged(@NonNull String username, @NonNull String password);

    Optional<AccountDto> signOut();

    Optional<AccountDto> getSignedInUser();
}
