package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.BookingService;
import com.epam.training.ticketservice.service.model.AccountDto;
import com.epam.training.ticketservice.service.model.BookingDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class AccountCommand {

    private final AccountService accountService;
    private final BookingService bookingService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AccountCommand(AccountService accountService, BookingService bookingService) {
        this.accountService = accountService;
        this.bookingService = bookingService;
    }

    @ShellMethod(key = "sign in", value = "Sign in with user privileges")
    public String signIn(String username, String password) {
        Optional<AccountDto> account = accountService.signIn(username, password);
        if (account.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        return "Successfully signed in";
    }

    @ShellMethod(key = "sign up", value = "Sign up with username and password")
    public String signUp(String username, String password) {
        Optional<AccountDto> account = accountService.signUp(username, password);
        if (account.isEmpty()) {
            return "Username already exists";
        }
        return "Successfully signed up";
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
        Optional<AccountDto> optionalAccount = accountService.getSignedInAccount();
        if (optionalAccount.isEmpty()) {
            return "You are not signed in";
        }
        AccountDto account = optionalAccount.get();
        if (account.getRole() == AccountDto.Role.ADMIN) {
            return String.format("Signed in with privileged account '%s'", account.getUsername());
        }
        String usernameLine = String.format("Signed in with account '%s'", account.getUsername());
        List<BookingDto> bookingDtoList = bookingService.list();
        if (bookingDtoList.isEmpty()) {
            return usernameLine + "\nYou have not booked any tickets yet";
        }
        return usernameLine + "\nYour previous bookings are\n" + bookingDtoList
                .stream()
                .map(bookingDto -> String.format("Seats %s on %s in room %s starting at %s for %d HUF",
                        bookingDto.getSeats()
                                .stream()
                                .map(seatDto -> String.format("(%d,%d)", seatDto.getRow(), seatDto.getCol()))
                                .collect(Collectors.joining(", ")),
                        bookingDto.getScreening().getMovie().getTitle(),
                        bookingDto.getScreening().getRoom().getName(),
                        bookingDto.getScreening().getStartTime().format(formatter),
                        bookingDto.getPrice()))
                .collect(Collectors.joining("\n"));
    }

}
