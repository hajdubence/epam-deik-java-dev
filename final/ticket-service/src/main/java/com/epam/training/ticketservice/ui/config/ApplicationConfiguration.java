package com.epam.training.ticketservice.ui.config;

import com.epam.training.ticketservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class ApplicationConfiguration {

    private final AccountService accountService;

    public ApplicationConfiguration(AccountService accountService) {
        this.accountService = accountService;
        accountService.signUpPrivileged("admin","admin");
    }
}

/*
@Component
public class ApplicationConfiguration implements CommandLineRunner {

    private final AccountService accountService;

    public ApplicationConfiguration(AccountService accountService) {
        System.out.println("CommandLineRunner constructor");
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) {
        System.out.println("CommandLineRunner run");
        accountService.signUpPrivileged("admin","admin");
    }
}
*/