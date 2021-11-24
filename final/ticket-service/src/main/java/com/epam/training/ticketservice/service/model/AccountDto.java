package com.epam.training.ticketservice.service.model;

import com.epam.training.ticketservice.persistence.entity.Account;
import lombok.Value;

@Value
public class AccountDto {
    String username;
    Account.Role role;
}
