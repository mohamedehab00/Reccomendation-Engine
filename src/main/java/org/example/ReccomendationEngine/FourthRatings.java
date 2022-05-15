package org.example.ReccomendationEngine;

import org.example.Filters.Filter;
import org.example.Filters.TrueFilter;
import org.example.MovieUtil.MovieDatabase;
import org.example.RateUtil.Rater;
import org.example.RateUtil.RaterDatabase;
import org.example.RateUtil.Rating;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class FourthRatings {
    public FourthRatings() {
        // default constructor
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
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

    private double scaleRating(double m, int rmin,int rmax, int tmin,int tmax){
        return ((m-rmin)/(rmax-rmin))*(tmax-tmin)+(tmin);
    }

    private double dotProduct(Rater me, Rater r){
        double dot = 0.0;

        Set<String> intersectSet = (me.getItemsRated()).stream()
                .filter((r.getItemsRated())::contains)
                .collect(Collectors.toSet());

        for (String movie:
                intersectSet) {
            double myRating = scaleRating(me.getRating(movie),0,10,-5,5);
            double rRating = scaleRating(r.getRating(movie),0,10,-5,5);

            dot += (myRating * rRating);
        }

        return dot;
    }

    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rater> raters = RaterDatabase.getRaters();
        ArrayList<Rating> ratings = new ArrayList<>();
        Rater rater = RaterDatabase.getRater(id);
        for (Rater currRater:
                raters) {
            if (rater.getID().equals(currRater.getID())){
                continue;
            }
            ratings.add(new Rating(currRater.getID(),dotProduct(rater,currRater)));
        }

        ratings.sort(new Comparator<Rating>() {
            @Override
            public int compare(Rating o1, Rating o2) {
                if (o1.getValue() < o2.getValue()){
                    return 1;
                } else if (o1.getValue() > o2.getValue()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });

        return ratings;
    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        ArrayList<Rating> ratings = new ArrayList<>();

        ArrayList<Rating> raters = getSimilarities(id);

        for (String movie: MovieDatabase.filterBy(new TrueFilter())) {
            double weightedAVG = 0.0;
            int numOfRaters = 0;
            for (int i = 0; i < numSimilarRaters && i < raters.size(); i++) {
                String raterId = raters.get(i).getItem();
                double raterSim = raters.get(i).getValue();
                if (raterSim >= 0.0){
                    double rate = RaterDatabase.getRater(raterId).getRating(movie);
                    if (rate == -1){
                        continue;
                    }
                    weightedAVG += (raterSim * rate);
                    numOfRaters+=1;
                }
            }
            if (numOfRaters >= minimalRaters){
                Rating rating = new Rating(movie,weightedAVG/numOfRaters);
                ratings.add(rating);
            }
        }

        ratings.sort(new Comparator<Rating>() {
            @Override
            public int compare(Rating o1, Rating o2) {
                if (o1.getValue() < o2.getValue()){
                    return 1;
                } else if (o1.getValue() > o2.getValue()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });

        return ratings;
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters,Filter filterCriteria){
        ArrayList<Rating> ratings = new ArrayList<>();

        ArrayList<Rating> raters = getSimilarities(id);

        for (String movie: MovieDatabase.filterBy(filterCriteria)) {
            double weightedAVG = 0.0;
            int numOfRaters = 0;
            for (int i = 0; i < numSimilarRaters && i < raters.size(); i++) {
                String raterId = raters.get(i).getItem();
                double raterSim = raters.get(i).getValue();
                if (raterSim >= 0.0){
                    double rate = RaterDatabase.getRater(raterId).getRating(movie);
                    if (rate == -1){
                        continue;
                    }
                    weightedAVG += (raterSim * rate);
                    numOfRaters+=1;
                }
            }
            if (numOfRaters >= minimalRaters){
                Rating rating = new Rating(movie,weightedAVG/numOfRaters);
                ratings.add(rating);
            }
        }

        ratings.sort(new Comparator<Rating>() {
            @Override
            public int compare(Rating o1, Rating o2) {
                if (o1.getValue() < o2.getValue()){
                    return 1;
                } else if (o1.getValue() > o2.getValue()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });

        return ratings;
    }
}
