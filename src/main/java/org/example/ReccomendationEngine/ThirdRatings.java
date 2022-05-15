package org.example.ReccomendationEngine;

import org.example.Filters.Filter;
import org.example.Filters.TrueFilter;
import org.example.MovieUtil.MovieDatabase;
import org.example.RateUtil.Rater;
import org.example.RateUtil.RaterDatabase;
import org.example.RateUtil.Rating;

import java.util.ArrayList;
import java.util.HashMap;

public class ThirdRatings {
    private HashMap<String, Rater> myRaters;

    public ThirdRatings() {
        // default constructor
        this("data/ratings.csv");
    }

    public ThirdRatings(String ratingsFile) {
        FirstRatings fr = new FirstRatings();
        this.myRaters = fr.loadRaters(ratingsFile);
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> ratings = new ArrayList<>();
        for (String currMovie:
                movies) {
            double avgRate = getAverageByID(currMovie,minimalRaters);
            if (avgRate == 0.0){
                continue;
            }
            Rating currRating = new Rating(currMovie,avgRate);
            ratings.add(currRating);
        }
        return ratings;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> ratings = new ArrayList<>();
        for (String currMovie:
                movies) {
            double avgRate = getAverageByID(currMovie,minimalRaters);
            if (avgRate == 0.0) {
                continue;
            }
            Rating currRating = new Rating(currMovie,avgRate);
            ratings.add(currRating);
        }
        return ratings;
    }
}
