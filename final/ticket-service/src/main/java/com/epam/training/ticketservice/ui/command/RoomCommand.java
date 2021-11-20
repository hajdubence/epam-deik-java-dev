package com.epam.training.ticketservice.ui.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class RoomCommand {

    @ShellMethod(key = "create room", value = "Creates a room")
    public String create(String name, int rows, int cols) {
        return "create room <terem neve> <széksorok száma> <szék oszlopok száma>";
    }

    @ShellMethod(key = "update room", value = "Updates a room")
    public String update(String name, int rows, int cols) {
        return "update room <terem neve> <széksorok száma> <szék oszlopok száma>";
    }

    @ShellMethod(key = "delete room", value = "Deletes a room")
    public String delete(String name) {
        return "delete room <terem neve>";
    }

    @ShellMethod(key = "list rooms", value = "Lists all rooms")
    public String list() {
        return "There are no rooms at the moment";
    }
}
