package com.epam.training.ticketservice.ui.config;

import com.epam.training.ticketservice.service.AccountService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("ci")
@Component
public class InMemoryDatabaseInitializer {

    private final AccountService accountService;

    public InMemoryDatabaseInitializer(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostConstruct
    public void init() {
        accountService.signUpPrivileged("admin","admin");
    }
}