package tech.xserver.xmovies.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.xserver.xmovies.exceptions.BadReqException;
import tech.xserver.xmovies.client.TMDBClient;
import tech.xserver.xmovies.validators.Validators;


@Controller
@RequestMapping("/")
public class MovieController {

    private final TMDBClient client;
    private final Validators validators;

    public MovieController(TMDBClient client, Validators validators) {
        this.client = client;
        this.validators = validators;
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/movies")
    @Retryable(maxAttempts = 5, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResponseEntity<Object> getMovies(){
        return new ResponseEntity<>(client.getMovies(), HttpStatus.OK);
    }

    @GetMapping("/shows")
    @Retryable(maxAttempts = 5, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResponseEntity<Object> getShows(@RequestParam(value = "page", defaultValue = "1", required = false) String pageNumber){
        if (!validators.isValidInteger(pageNumber)) {
            throw new BadReqException("Page number must be a valid integer within the range of an integer.");
        }
        return new ResponseEntity<>(client.getShows(Integer.parseInt(pageNumber)).getBody(), HttpStatus.OK);
    }

    @GetMapping("/trending")
    @Retryable(maxAttempts = 5, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResponseEntity<Object> getTrending(
            @RequestParam(value = "time_window", defaultValue = "day", required = false) String timeWindow,
            @RequestParam(value = "page", defaultValue = "1", required = false) String pageNumber
    ) throws BadReqException {
        try {
            if (!validators.isValidTimeWindow(timeWindow)) {
                throw new BadReqException("Invalid time window (must be 'day' or 'week').");
            }
            if (!validators.isValidInteger(pageNumber)) {
                throw new BadReqException("Page number must be a valid integer within the range of an integer.");
            }
            return new ResponseEntity<>(client.getTrending(timeWindow.toLowerCase(), Integer.parseInt(pageNumber)), HttpStatus.OK);
        } catch (BadReqException e) {
            throw new BadReqException(e.getMessage());
        }
    }
}
