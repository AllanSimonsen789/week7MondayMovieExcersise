/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Movie;
import java.util.List;

/**
 *
 * @author allan
 */
public class MoviesDTO {
    private List<Movie> movies;

    public MoviesDTO(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
    
}