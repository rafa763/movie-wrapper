package tech.xserver.xmovies.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.xserver.xmovies.exceptions.BadReqException;
import tech.xserver.xmovies.services.MovieService;
import tech.xserver.xmovies.validators.TimeWindow;


@Controller
@Validated
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getMovies(@RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) String pageNumber){
        return new ResponseEntity<>(service.getMovies(Integer.parseInt(pageNumber)), HttpStatus.OK);
    }

    @GetMapping("/shows")
    public ResponseEntity<?> getShows(@RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) String pageNumber){
        return new ResponseEntity<>(service.getShows(Integer.parseInt(pageNumber)).getBody(), HttpStatus.OK);
    }

    @GetMapping("/trending")
    public ResponseEntity<?> getTrending(
            @RequestParam(value = "time_window", defaultValue = "day", required = false) @Valid TimeWindow timeWindow,
            @RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) @Max(8500) String pageNumber,
            @RequestParam(value = "size", defaultValue = "20", required = false) @Min(1) @Max(50) String size
    ) {
        return new ResponseEntity<>(
                service.getTrending(String.valueOf(timeWindow),
                        Integer.parseInt(pageNumber),
                        Integer.parseInt(size)),
                HttpStatus.OK
        );
    }
}
