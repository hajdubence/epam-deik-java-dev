package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.model.MovieDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class MovieCommand extends CommandAvailability {

    private final MovieService movieService;

    public MovieCommand(MovieService movieService) {
        this.movieService = movieService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create movie", value = "Creates a movie")
    public String create(String title, String genre, int length) {
        if (movieService.create(title, genre, length).isEmpty()) {
            return "Fail";
        }
        return "Success";
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update movie", value = "Updates a movie")
    public String update(String title, String genre, int length) {
        if (movieService.update(title, genre, length).isEmpty()) {
            return "Fail";
        }
        return "Success";
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete movie", value = "Deletes a movie")
    public String delete(String title) {
        if (movieService.delete(title).isEmpty()) {
            return "Fail";
        }
        return "Success";
    }

    @ShellMethod(key = "list movies", value = "Lists all movies")
    public String list() {
        List<MovieDto> movieDtoList = movieService.list();
        if (movieDtoList.isEmpty()) {
            return "There are no movies at the moment";
        }
        return movieDtoList
                .stream()
                .map(movieDto -> String.format("%s (%s, %d minutes)",
                        movieDto.getTitle(), movieDto.getGenre(), movieDto.getLength()))
                .collect(Collectors.joining("\n"));
    }

}
