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

    private AccountDto signedInAccount = null;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    AccountServiceImpl(AccountDto signedInAccount, AccountRepository accountRepository) {
        this.signedInAccount = signedInAccount;
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<AccountDto> signUp(@NonNull String username, @NonNull String password) {
        if (accountRepository.findByUsername(username).isPresent()) {
            return Optional.empty();
        }
        Account account = new Account(username, password, Account.Role.USER);
        return Optional.of(new AccountDto(accountRepository.save(account)));
    }

    @Override
    public Optional<AccountDto> signUpPrivileged(@NonNull String username, @NonNull String password) {
        if (accountRepository.findByUsername(username).isPresent()) {
            return Optional.empty();
        }
        Account account = new Account(username, password, Account.Role.ADMIN);
        return Optional.of(new AccountDto(accountRepository.save(account)));
    }

    @Override
    public Optional<AccountDto> signIn(@NonNull String username, @NonNull String password) {
        Optional<Account> optionalAccount = accountRepository
                .findByUsernameAndPasswordAndRole(username, password, Account.Role.USER);
        if (optionalAccount.isEmpty()) {
            return Optional.empty();
        }
        signedInAccount = new AccountDto(optionalAccount.get());
        return getSignedInAccount();
    }

    @Override
    public Optional<AccountDto> signInPrivileged(@NonNull String username, @NonNull String password) {
        Optional<Account> optionalAccount = accountRepository
                .findByUsernameAndPasswordAndRole(username, password, Account.Role.ADMIN);
        if (optionalAccount.isEmpty()) {
            return Optional.empty();
        }
        signedInAccount = new AccountDto(optionalAccount.get());
        return getSignedInAccount();
    }

    @Override
    public Optional<AccountDto> signOut() {
        Optional<AccountDto> previouslySignedInUser = getSignedInAccount();
        signedInAccount = null;
        return previouslySignedInUser;
    }

    @Override
    public Optional<AccountDto> getSignedInAccount() {
        return Optional.ofNullable(signedInAccount);
    }

}
