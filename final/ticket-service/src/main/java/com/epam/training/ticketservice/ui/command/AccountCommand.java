package com.epam.training.ticketservice.ui.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AccountCommand {

    @ShellMethod(key = "sign in privileged", value = "Sign in with administrator privileges")
    public String signIn(String username, String password) {
        return "Login failed due to incorrect credentials";
    }

    @ShellMethod(key = "sign out", value = "Sign out")
    public String signOut() {
        return "You are not signed in";
    }

    @ShellMethod(key = "describe account", value = "Account information")
    public String describeAccount() {
        return "You are not signed in";
    }
}
