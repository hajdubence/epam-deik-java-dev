package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.persistence.entity.Account;
import com.epam.training.ticketservice.persistence.repository.AccountRepository;
import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.model.AccountDto;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountDto signedInUser = null;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    AccountServiceImpl(AccountDto signedInUser, AccountRepository accountRepository) {
        this.signedInUser = signedInUser;
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<AccountDto> signUp(@NonNull String username, @NonNull String password) {
        Account account = new Account(username, password, Account.Role.USER);
        return Optional.ofNullable(accountToAccountDto(accountRepository.save(account)));
    }

    @Override
    public Optional<AccountDto> signUpPrivileged(@NonNull String username, @NonNull String password) {
        Account account = new Account(username, password, Account.Role.ADMIN);
        return Optional.ofNullable(accountToAccountDto(accountRepository.save(account)));
    }

    @Override
    public Optional<AccountDto> signIn(@NonNull String username, @NonNull String password) {
        Optional<Account> optionalAccount = accountRepository
                .findByUsernameAndPasswordAndRole(username, password, Account.Role.USER);
        if (optionalAccount.isEmpty()) {
            return Optional.empty();
        }
        signedInUser = accountToAccountDto(optionalAccount.get());
        return getSignedInUser();
    }

    @Override
    public Optional<AccountDto> signInPrivileged(@NonNull String username, @NonNull String password) {
        Optional<Account> optionalAccount = accountRepository
                .findByUsernameAndPasswordAndRole(username, password, Account.Role.ADMIN);
        if (optionalAccount.isEmpty()) {
            return Optional.empty();
        }
        signedInUser = accountToAccountDto(optionalAccount.get());
        return getSignedInUser();
    }

    @Override
    public Optional<AccountDto> signOut() {
        Optional<AccountDto> previouslySignedInUser = getSignedInUser();
        signedInUser = null;
        return previouslySignedInUser;
    }

    @Override
    public Optional<AccountDto> getSignedInUser() {
        return Optional.ofNullable(signedInUser);
    }

    private AccountDto accountToAccountDto(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountDto(account.getUsername(), account.getRole());
    }

}
