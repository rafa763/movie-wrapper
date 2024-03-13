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

    @Retryable(maxAttempts = 6, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResponseEntity<Object> getMovies(Integer page) {
        return client.getMovies(page);
    }

    @Retryable(maxAttempts = 5, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResponseEntity<Object> getShows(Integer page) {
        return client.getShows(page);
    }

    @Retryable(maxAttempts = 5, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResponseEntity<Object> getTrending(String timeWindow, Integer page, Integer size) {
        return client.getTrending(timeWindow, page, size);
    }


}
