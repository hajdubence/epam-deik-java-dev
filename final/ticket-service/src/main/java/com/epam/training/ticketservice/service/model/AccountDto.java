package com.epam.training.ticketservice.service.model;

import com.epam.training.ticketservice.persistence.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class AccountDto {

    String username;
    Role role;

    public enum Role {
        USER,
        ADMIN
    }

    public AccountDto(Account account) {
        username = account.getUsername();
        switch (account.getRole()) {
            case USER:
                role = Role.USER;
                break;
            case ADMIN:
                role = Role.ADMIN;
                break;
            default:
                role = null;
                break;
        }
    }

}
