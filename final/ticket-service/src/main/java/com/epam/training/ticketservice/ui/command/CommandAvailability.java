package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.persistence.entity.Account;
import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.model.AccountDto;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommandAvailability {

    private final AccountService accountService;

    public CommandAvailability(AccountService accountService) {
        this.accountService = accountService;
    }

    public Availability isAdmin() {
        Optional<AccountDto> optionalAccountDto = accountService.getSignedInUser();
        if (optionalAccountDto.isEmpty()) {
            return Availability.unavailable("You are not signed in");
        }
        if (optionalAccountDto.get().getRole() == Account.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You dont have permissions");
    }
}
