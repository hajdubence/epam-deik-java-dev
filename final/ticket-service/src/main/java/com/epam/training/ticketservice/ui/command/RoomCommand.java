package com.epam.training.ticketservice.ui.command;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class RoomCommand {

    private final CommandAvailability commandAvailability;

    public RoomCommand(CommandAvailability commandAvailability) {
        this.commandAvailability = commandAvailability;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create room", value = "Creates a room")
    public String create(String name, int rows, int cols) {
        return "create room <terem neve> <széksorok száma> <szék oszlopok száma>";
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update room", value = "Updates a room")
    public String update(String name, int rows, int cols) {
        return "update room <terem neve> <széksorok száma> <szék oszlopok száma>";
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete room", value = "Deletes a room")
    public String delete(String name) {
        return "delete room <terem neve>";
    }

    @ShellMethod(key = "list rooms", value = "Lists all rooms")
    public String list() {
        return "There are no rooms at the moment";
    }

    private Availability isAdmin() {
        return commandAvailability.isAdmin();
    }
}
