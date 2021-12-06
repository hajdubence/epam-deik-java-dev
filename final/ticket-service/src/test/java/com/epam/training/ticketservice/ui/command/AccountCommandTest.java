package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.BookingService;
import com.epam.training.ticketservice.service.model.AccountDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountCommandTest {

    private final AccountService accountService = mock(AccountService.class);
    private final BookingService bookingService = mock(BookingService.class);
    private final AccountCommand underTest = new AccountCommand(accountService, bookingService);

    @Test
    void signInWhenCredentialsIncorrect() {
        String expected = "Login failed due to incorrect credentials";
        when(accountService.signIn("username", "password")).thenReturn(Optional.empty());

        String actual = underTest.signIn("username", "password");

        assertEquals(expected, actual);
        verify(accountService).signIn("username", "password");
    }

    @Test
    void signInWhenCredentialsCorrect() {
        String expected = "Successfully signed in";
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.USER);
        when(accountService.signIn("username", "password")).thenReturn(Optional.of(accountDto));

        String actual = underTest.signIn("username", "password");

        assertEquals(expected, actual);
        verify(accountService).signIn("username", "password");
    }

    @Test
    void signUpWhenUsernameIsTaken() {
        String expected = "Username already exists";
        when(accountService.signUp("username", "password")).thenReturn(Optional.empty());

        String actual = underTest.signUp("username", "password");

        assertEquals(expected, actual);
        verify(accountService).signUp("username", "password");
    }

    @Test
    void signUpWhenUsernameIsAvailable() {
        String expected = "Successfully signed up";
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.USER);
        when(accountService.signUp("username", "password")).thenReturn(Optional.of(accountDto));

        String actual = underTest.signUp("username", "password");

        assertEquals(expected, actual);
        verify(accountService).signUp("username", "password");
    }

    @Test
    void signInPrivilegedWhenCredentialsIncorrect() {
        String expected = "Login failed due to incorrect credentials";
        when(accountService.signInPrivileged("username", "password")).thenReturn(Optional.empty());

        String actual = underTest.signInPrivileged("username", "password");

        assertEquals(expected, actual);
        verify(accountService).signInPrivileged("username", "password");
    }

    @Test
    void signInPrivilegedWhenCredentialsCorrect() {
        String expected = "Successfully signed in";
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.ADMIN);
        when(accountService.signInPrivileged("username", "password")).thenReturn(Optional.of(accountDto));

        String actual = underTest.signInPrivileged("username", "password");

        assertEquals(expected, actual);
        verify(accountService).signInPrivileged("username", "password");
    }

    @Test
    void signOutWhenNotSignedIn() {
        String expected = "You are not signed in";
        when(accountService.signOut()).thenReturn(Optional.empty());

        String actual = underTest.signOut();

        assertEquals(expected, actual);
        verify(accountService).signOut();
    }

    @Test
    void signOutWhenSignedIn() {
        String expected = "Successfully signed out";
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.USER);
        when(accountService.signOut()).thenReturn(Optional.of(accountDto));

        String actual = underTest.signOut();

        assertEquals(expected, actual);
        verify(accountService).signOut();
    }

    @Test
    void describeAccountWhenNotSignedIn() {
        String expected = "You are not signed in";
        when(accountService.getSignedInAccount()).thenReturn(Optional.empty());

        String actual = underTest.describeAccount();

        assertEquals(expected, actual);
        verify(accountService).getSignedInAccount();
    }

    @Test
    void describeAccountWhenSignedInWithAdminAccount() {
        String expected = "Signed in with privileged account 'username'";
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.ADMIN);
        when(accountService.getSignedInAccount()).thenReturn(Optional.of(accountDto));

        String actual = underTest.describeAccount();

        assertEquals(expected, actual);
        verify(accountService).getSignedInAccount();
    }

    @Test
    void describeAccountWhenSignedInWithUserAccountWithNoBookings() {
        String expected = "Signed in with account 'username'\nYou have not booked any tickets yet";
        AccountDto accountDto = new AccountDto("username", AccountDto.Role.USER);
        when(accountService.getSignedInAccount()).thenReturn(Optional.of(accountDto));
        when(bookingService.list()).thenReturn(List.of());

        String actual = underTest.describeAccount();

        assertEquals(expected, actual);
        verify(accountService).getSignedInAccount();
        verify(bookingService).list();
    }
}