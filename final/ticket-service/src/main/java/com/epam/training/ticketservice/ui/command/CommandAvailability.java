package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.model.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public abstract class CommandAvailability {

    @Autowired
    private AccountService accountService;

    public Availability isAdmin() {
        Optional<AccountDto> optionalAccountDto = accountService.getSignedInAccount();
        if (optionalAccountDto.isEmpty()) {
            return Availability.unavailable("You are not signed in");
        }
        if (optionalAccountDto.get().getRole() == AccountDto.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You dont have permissions");
    }

    public Availability isUser() {
        Optional<AccountDto> optionalAccountDto = accountService.getSignedInAccount();
        if (optionalAccountDto.isEmpty()) {
            return Availability.unavailable("You are not signed in");
        }
        if (optionalAccountDto.get().getRole() == AccountDto.Role.USER) {
            return Availability.available();
        }
        return Availability.unavailable("You dont have permissions");
    }

}
