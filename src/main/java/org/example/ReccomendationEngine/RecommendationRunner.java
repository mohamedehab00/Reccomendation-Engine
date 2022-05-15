package org.example.ReccomendationEngine;

import org.example.Filters.YearAfterFilter;
import org.example.MovieUtil.MovieDatabase;
import org.example.RateUtil.Rater;
import org.example.RateUtil.RaterDatabase;
import org.example.RateUtil.Rating;

import java.util.ArrayList;

public class RecommendationRunner implements Recommender{
    @Override
    public ArrayList<String> getItemsToRate() {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        return MovieDatabase.filterBy(new YearAfterFilter(2000));
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> ratingList = fr.getSimilarRatings(webRaterID, 20, 5);

        System.out.println("Found ratings for movies : " + ratingList.size());

        for (int i = 0; i < ratingList.size() && i < 20; i++) {
            System.out.println(MovieDatabase.getMovie(ratingList.get(i).getItem()));
        }
    }
}
