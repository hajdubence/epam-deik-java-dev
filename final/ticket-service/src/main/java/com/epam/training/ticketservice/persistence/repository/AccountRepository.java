package com.epam.training.ticketservice.persistence.repository;

import com.epam.training.ticketservice.persistence.entity.Account;
import com.epam.training.ticketservice.persistence.entity.Account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByUsernameAndPasswordAndRole(String username, String password, Role role);

    Optional<Account> findByUsername(String username);

}