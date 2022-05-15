package org.example.MovieUtil;

import org.example.RateUtil.Rating;
import org.example.ReccomendationEngine.SecondRatings;

import java.util.ArrayList;
import java.util.Comparator;

public class MovieRunnerAverage {
    public void printAverageRatings(int minRaters){
        SecondRatings sr = new SecondRatings();

        System.out.println("Number Of Movies Loaded : "+sr.getMovieSize()+" - Number Of Raters : "+sr.getRaterSize());

        ArrayList<Rating> ratings = sr.getAverageRatings(minRaters);

        ratings.sort(new Comparator<Rating>() {
            @Override
            public int compare(Rating o1, Rating o2) {
                if (o1.getValue() > o2.getValue()){
                    return 1;
                } else if (o1.getValue() < o2.getValue()) {
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });

        for (Rating rate:
                ratings) {
            if(rate.getValue() > 0.0){
                String title = sr.getTitle(rate.getItem());
                double avgRate = rate.getValue();

                System.out.println(title + " " +rate.getValue());
            }
        }
    }

    public void getAverageRatingOneMovie(int minRaters){
        SecondRatings sr = new SecondRatings();
        String movieTitle = "Vacation";
        String movieId = sr.getID(movieTitle);
        double avgMovieRate = sr.getAverageByID(movieId, minRaters);
        System.out.println(avgMovieRate);
    }
}
