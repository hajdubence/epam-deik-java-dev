package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.model.RoomDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class RoomCommand {

    private final CommandAvailability commandAvailability;
    private final RoomService roomService;

    public RoomCommand(CommandAvailability commandAvailability, RoomService roomService) {
        this.commandAvailability = commandAvailability;
        this.roomService = roomService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create room", value = "Creates a room")
    public String create(String name, int rows, int cols) {
        if (roomService.create(name, rows, cols).isEmpty()) {
            return "Fail";
        }
        return "Success";
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update room", value = "Updates a room")
    public String update(String name, int rows, int cols) {
        if (roomService.update(name, rows, cols).isEmpty()) {
            return "Fail";
        }
        return "Success";
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete room", value = "Deletes a room")
    public String delete(String name) {
        if (roomService.delete(name).isEmpty()) {
            return "Fail";
        }
        return "Success";
    }

    @ShellMethod(key = "list rooms", value = "Lists all rooms")
    public String list() {
        List<RoomDto> roomDtoList = roomService.list();
        if (roomDtoList.isEmpty()) {
            return "There are no rooms at the moment";
        }
        return roomDtoList
                .stream()
                .map(roomDto -> String.format("Room %s with %d seats, %d rows and %d columns",
                        roomDto.getName(), roomDto.getRows() * roomDto.getCols(), roomDto.getRows(), roomDto.getCols()))
                .collect(Collectors.joining("\n"));
    }

    private Availability isAdmin() {
        return commandAvailability.isAdmin();
    }
}
