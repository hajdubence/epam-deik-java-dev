package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.model.AccountDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
public class AccountCommand {

    private final AccountService accountService;

    public AccountCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @ShellMethod(key = "sign in privileged", value = "Sign in with administrator privileges")
    public String signInPrivileged(String username, String password) {
        Optional<AccountDto> account = accountService.signInPrivileged(username, password);
        if (account.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        return "Successfully signed in";
    }

    @ShellMethod(key = "sign out", value = "Sign out")
    public String signOut() {
        Optional<AccountDto> account = accountService.signOut();
        if (account.isEmpty()) {
            return "You are not signed in";
        }
        return "Successfully signed out";
    }

    @ShellMethod(key = "describe account", value = "Account information")
    public String describeAccount() {
        Optional<AccountDto> account = accountService.getSignedInUser();
        if (account.isEmpty()) {
            return "You are not signed in";
        }
        return String.format("Signed in with privileged account '%s'", account.get().getUsername());
    }
}
