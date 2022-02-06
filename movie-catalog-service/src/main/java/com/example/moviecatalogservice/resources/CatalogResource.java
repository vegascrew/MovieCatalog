package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        //1. Get all rated movie id
        //2. For each movie id, call movie info service and get details
        //3. Put them all together

        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5678", 3)
        );
        return ratings.stream()
                .map(rating -> {
                    //return response is string, unmarshalling of response to Movie object.
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), "Description", rating.getRating());
                })
                .collect(Collectors.toList());
    }
}
