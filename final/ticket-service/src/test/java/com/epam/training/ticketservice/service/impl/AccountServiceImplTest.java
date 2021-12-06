package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.persistence.entity.Account;
import com.epam.training.ticketservice.persistence.repository.AccountRepository;
import com.epam.training.ticketservice.service.model.AccountDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final AccountServiceImpl underTest = new AccountServiceImpl(accountRepository);
    private final AccountDto signedInAccountDto = new AccountDto("username", AccountDto.Role.USER);
    private final AccountServiceImpl underTest2 = new AccountServiceImpl(signedInAccountDto, accountRepository);

    @Test
    void signUpShouldReturnOptionalEmptyWhenUsernameIsTaken() {
        Optional<AccountDto> expected = Optional.empty();
        Account account = new Account("username", "pass", Account.Role.USER);
        when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));

        Optional<AccountDto> actual = underTest.signUp("username", "password");

        assertEquals(expected, actual);
        verify(accountRepository).findByUsername("username");
    }

    @Test
    void signUpShouldReturnAccountDtoWhenUsernameIsAvailable() {
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.USER);
        Optional<AccountDto> expected = Optional.of(accountDto);
        when(accountRepository.findByUsername("username")).thenReturn(Optional.empty());
        Account account = new Account("username", "password", Account.Role.USER);
        when(accountRepository.save(account)).thenReturn(account);

        Optional<AccountDto> actual = underTest.signUp("username", "password");

        assertEquals(expected, actual);
        verify(accountRepository).findByUsername("username");
        verify(accountRepository).save(account);
    }

    @Test
    void signUpPrivilegedShouldReturnOptionalEmptyWhenUsernameIsTaken() {
        Optional<AccountDto> expected = Optional.empty();
        Account account = new Account("username", "pass", Account.Role.ADMIN);
        when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));

        Optional<AccountDto> actual = underTest.signUpPrivileged("username", "password");

        assertEquals(expected, actual);
        verify(accountRepository).findByUsername("username");
    }

    @Test
    void signUpPrivilegedShouldReturnAccountDtoWhenUsernameIsAvailable() {
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.ADMIN);
        Optional<AccountDto> expected = Optional.of(accountDto);
        when(accountRepository.findByUsername("username")).thenReturn(Optional.empty());
        Account account = new Account("username", "password", Account.Role.ADMIN);
        when(accountRepository.save(account)).thenReturn(account);

        Optional<AccountDto> actual = underTest.signUpPrivileged("username", "password");

        assertEquals(expected, actual);
        verify(accountRepository).findByUsername("username");
        verify(accountRepository).save(account);
    }

    @Test
    void signInShouldReturnOptionalEmptyWhenUsernameOrPasswordAreIncorrect() {
        Optional<AccountDto> expected = Optional.empty();
        when(accountRepository.findByUsernameAndPasswordAndRole("username", "password", Account.Role.USER))
                .thenReturn(Optional.empty());

        Optional<AccountDto> actual = underTest.signIn("username", "password");

        assertEquals(expected, actual);
        verify(accountRepository).findByUsernameAndPasswordAndRole("username", "password", Account.Role.USER);
    }

    @Test
    void signInShouldReturnAccountDtoWhenUsernameAndPasswordAreCorrect() {
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.USER);
        Optional<AccountDto> expected = Optional.of(accountDto);
        Account account = new Account("username", "password", Account.Role.USER);
        when(accountRepository.findByUsernameAndPasswordAndRole("username", "password", Account.Role.USER))
                .thenReturn(Optional.of(account));

        Optional<AccountDto> actual = underTest.signIn("username", "password");

        assertEquals(expected, actual);
        verify(accountRepository).findByUsernameAndPasswordAndRole("username", "password", Account.Role.USER);
    }

    @Test
    void signInPrivilegedShouldReturnOptionalEmptyWhenUsernameOrPasswordAreIncorrect() {
        Optional<AccountDto> expected = Optional.empty();
        when(accountRepository.findByUsernameAndPasswordAndRole("username", "password", Account.Role.ADMIN))
                .thenReturn(Optional.empty());

        Optional<AccountDto> actual = underTest.signInPrivileged("username", "password");

        assertEquals(expected, actual);
        verify(accountRepository).findByUsernameAndPasswordAndRole("username", "password", Account.Role.ADMIN);
    }

    @Test
    void signInPrivilegedShouldReturnAccountDtoWhenUsernameAndPasswordAreCorrect() {
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.ADMIN);
        Optional<AccountDto> expected = Optional.of(accountDto);
        Account account = new Account("username", "password", Account.Role.ADMIN);
        when(accountRepository.findByUsernameAndPasswordAndRole("username", "password", Account.Role.ADMIN))
                .thenReturn(Optional.of(account));

        Optional<AccountDto> actual = underTest.signInPrivileged("username", "password");

        assertEquals(expected, actual);
        verify(accountRepository).findByUsernameAndPasswordAndRole("username", "password", Account.Role.ADMIN);
    }

    @Test
    void signOutShouldReturnOptionalEmptyWhenThereIsNoOneSignedIn() {
        Optional<AccountDto> expected = Optional.empty();

        Optional<AccountDto> actual = underTest.signOut();

        assertEquals(expected, actual);
    }

    @Test
    void signOutShouldReturnAccountDtoOfTheThePreviouslySignedInAccount() {
        Optional<AccountDto> expected = Optional.of(signedInAccountDto);

        Optional<AccountDto> actual = underTest2.signOut();

        assertEquals(expected, actual);
    }

    @Test
    void getSignedInAccountShouldReturnOptionalEmptyWhenNotSignedIn() {
        Optional<AccountDto> expected = Optional.empty();

        Optional<AccountDto> actual = underTest.getSignedInAccount();

        assertEquals(expected, actual);
    }

    @Test
    void getSignedInAccountShouldReturnAccountDtoOfTheTheSignedInAccountWhenSignedIn() {
        Optional<AccountDto> expected = Optional.of(signedInAccountDto);

        Optional<AccountDto> actual = underTest2.getSignedInAccount();

        assertEquals(expected, actual);
    }
}