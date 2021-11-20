package com.epam.training.ticketservice.ui.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ScreeningCommand {

    @ShellMethod(key = "create screening", value = "Creates a screening")
    public String create(String movieTitle, String roomName, String start) {
        return "create screening <film címe> <terem neve> "
               + "<vetítés kezdetének dátuma és ideje, YYYY-MM-DD hh:mm formátumban>";
    }

    @ShellMethod(key = "delete screening", value = "Deletes a screening")
    public String delete(String movieTitle, String roomName, String start) {
        return "delete screening <film címe> <terem neve> "
               + "<vetítés kezdetének dátuma és ideje, YYYY-MM-DD hh:mm formátumban>";
    }

    @ShellMethod(key = "list screenings", value = "Lists all screenings")
    public String list() {
        return "There are no screenings";
    }
}
