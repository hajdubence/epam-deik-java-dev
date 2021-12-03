package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.model.ScreeningDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class ScreeningCommand extends CommandAvailability {

    private final ScreeningService screeningService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ScreeningCommand(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create screening", value = "Creates a screening")
    public String create(String movieTitle, String roomName, String start) {
        LocalDateTime startTime;
        try {
            startTime = LocalDateTime.parse(start, formatter);
        } catch (DateTimeParseException e) {
            return "Parse fail YYYY-MM-DD hh:mm";
        }
        try {
            if (screeningService.create(movieTitle, roomName, startTime).isPresent()) {
                return "Success";
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return "Fail";
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete screening", value = "Deletes a screening")
    public String delete(String movieTitle, String roomName, String start) {
        LocalDateTime startTime;
        try {
            startTime = LocalDateTime.parse(start, formatter);
        } catch (DateTimeParseException e) {
            return "Parse fail YYYY-MM-DD hh:mm";
        }
        if (screeningService.delete(movieTitle, roomName, startTime).isPresent()) {
            return "Success";
        }
        return "Fail";
    }

    @ShellMethod(key = "list screenings", value = "Lists all screenings")
    public String list() {
        List<ScreeningDto> screeningDtoList = screeningService.list();
        if (screeningDtoList.isEmpty()) {
            return "There are no screenings";
        }
        return screeningDtoList
                .stream()
                .map(screeningDto -> String.format("%s (%s, %d minutes), screened in room %s, at %s",
                        screeningDto.getMovie().getTitle(), screeningDto.getMovie().getGenre(),
                        screeningDto.getMovie().getLength(), screeningDto.getRoom().getName(),
                        screeningDto.getStartTime().format(formatter)))
                .collect(Collectors.joining("\n"));
    }

}