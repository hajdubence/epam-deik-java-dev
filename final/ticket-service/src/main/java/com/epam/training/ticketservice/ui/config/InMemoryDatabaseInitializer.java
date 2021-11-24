package com.epam.training.ticketservice.ui.config;

import com.epam.training.ticketservice.service.AccountService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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