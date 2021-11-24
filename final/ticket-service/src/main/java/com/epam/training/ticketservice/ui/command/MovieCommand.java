package com.epam.training.ticketservice.ui.command;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class MovieCommand {

    private final CommandAvailability commandAvailability;

    public MovieCommand(CommandAvailability commandAvailability) {
        this.commandAvailability = commandAvailability;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create movie", value = "Creates a movie")
    public String create(String title, String genre, int length) {
        return "create movie <film címe> <műfaj> <vetítés hossza percben>";
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update movie", value = "Updates a movie")
    public String update(String title, String genre, int length) {
        return "update movie <film címe> <műfaj> <vetítés hossza percben>";
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete movie", value = "Deletes a movie")
    public String delete(String title) {
        return "delete movie <film címe>";
    }

    @ShellMethod(key = "list movies", value = "Lists all movies")
    public String list() {
        return "There are no movies at the moment";
    }

    private Availability isAdmin() {
        return commandAvailability.isAdmin();
    }
}
