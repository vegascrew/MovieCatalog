package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import com.example.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        //1. Get all rated movie id
        //2. For each movie id, call movie info service and get details
        //3. Put them all together

        // Get all rated movie id
        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);


        return userRating.getRatings().stream()
                .map(rating -> {
                    // For each movie id, call movie info service and get details
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class); // return response is string, unmarshalling of response to Movie object.

                    // Put them all together
                    return new CatalogItem(movie.getName(), "Description", rating.getRating());
                })
                .collect(Collectors.toList());
    }

    /*
    get : http://localhost:8081/catalog/harshit
    Sample Response : [{"name":"Name for ID 1234","desc":"Description","rating":3},{"name":"Name for ID 5678","desc":"Description","rating":4}]
    */
}

/*
Alternative WebClient Way. Reactive side.
Movie movie = webClientBuilder.build()
              .get()
               .uri("http://localhost:8082/movies/"+rating.getMovieId())
               .retrieve()
               .bodyToMono(Movie.class)
               .block();
*/
