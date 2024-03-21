package tech.xserver.xmovies.services;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import tech.xserver.xmovies.client.TMDBClient;

@Service
public class MovieService {
    private final TMDBClient client;

    public MovieService(TMDBClient client) {
        this.client = client;
    }

    @Retryable(maxAttempts = 2, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public Object getMovies(Integer page) {
        return client.getMovies(page).getBody();
    }

    @Retryable(maxAttempts = 2, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public Object getMovie(Long id) {
        return client.getMovie(id).getBody();
    }

    @Retryable(maxAttempts = 2, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public Object getShows(Integer page) {
        return client.getShows(page).getBody();
    }

    @Retryable(maxAttempts = 2, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public Object getTrending(String timeWindow, Integer page, Integer size) {
        return client.getTrending(timeWindow, page, size).getBody();
    }


}
