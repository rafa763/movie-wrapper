package tech.xserver.xmovies.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("TMDB")
public interface TMDBClient {
    @RequestMapping(method = RequestMethod.GET, value ="/discover/movie")
    ResponseEntity<Object> getMovies();

    @RequestMapping(method = RequestMethod.GET, value ="/discover/tv")
    ResponseEntity<Object> getShows(@RequestParam("page") Integer pageNumber);

    @RequestMapping(method = RequestMethod.GET, value ="/trending/all/{time_window}")
    ResponseEntity<Object> getTrending(@PathVariable("time_window") String timeWindow, @RequestParam("page") Integer pageNumber);
}
