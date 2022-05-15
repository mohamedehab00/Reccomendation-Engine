package org.example.ReccomendationEngine;

import org.example.MovieUtil.Movie;
import org.example.RateUtil.Rater;
import org.example.RateUtil.RaterDatabase;
import org.example.RateUtil.Rating;

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private HashMap<String, Rater> myRaters;

    public SecondRatings() {
        // default constructor
        this("data/ratedmoviesfull.csv ", "data/ratings.csv");
    }

    public SecondRatings(String moviesFile,String ratingsFile) {
        FirstRatings fr = new FirstRatings();
        this.myMovies = fr.loadMovies(moviesFile);
        this.myRaters = fr.loadRaters(ratingsFile);
    }

    public int getMovieSize(){
        return myMovies.size();
    }

    public int getRaterSize(){
        return myRaters.size();
    }

    public double getAverageByID(String movieId, int minimalRaters){
        FirstRatings fr = new FirstRatings();

        double[] rateInfo = fr.ratingsParticularMovieHas(RaterDatabase.getRaters(),movieId);

        if (rateInfo[0] < minimalRaters){
            return 0.0;
        }
        else {
           return rateInfo[1] / rateInfo[0];
        }
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> ratings = new ArrayList<>();
        for (Movie currMovie:
             myMovies) {
            double avgRate = getAverageByID(currMovie.getID(),minimalRaters);
            Rating currRating = new Rating(currMovie.getID(),avgRate);
            ratings.add(currRating);
        }
        return ratings;
    }

    public String getTitle(String movieId){
        for (Movie currMovie:
             myMovies) {
            if (currMovie.getID() == movieId){
                return currMovie.getTitle();
            }
        }
        return "ID was not found";
    }

    public String getID(String movieTitle){
        for (Movie currMovie:
                myMovies) {
            if (currMovie.getTitle().equals(movieTitle)){
                return currMovie.getID();
            }
        }
        return "NO SUCH TITLE";
    }
}
