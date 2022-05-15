package org.example.MovieUtil;

import org.example.Filters.*;
import org.example.ReccomendationEngine.FourthRatings;
import org.example.RateUtil.RaterDatabase;
import org.example.RateUtil.Rating;

import java.util.*;

public class MovieRunnerSimilarRatings {
    public void printAverageRatings(int minRaters){
        FourthRatings fourthRatings = new FourthRatings();

        System.out.println("Number Of Movies Loaded : "+MovieDatabase.size()+" - Number Of Raters : "+ RaterDatabase.size());

        ArrayList<Rating> ratings = fourthRatings.getAverageRatings(minRaters);

        System.out.println("Size : "+ratings.size());

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
                String title = MovieDatabase.getTitle(rate.getItem());
                double avgRate = rate.getValue();

                System.out.println(title + " " +rate.getValue());
            }
        }
    }

    public void printAverageRatingsByYearAfterAndGenre(int minRaters, int year, String genere){
        Filter f = new AllFilters();
        ((AllFilters) f).addFilter(new YearAfterFilter(year));
        ((AllFilters) f).addFilter(new GenreFilter(genere));


        FourthRatings fourthRatings = new FourthRatings();
        System.out.println("Number Of Movies Loaded : "+MovieDatabase.size()+" - Number Of Raters : "+RaterDatabase.size());

        ArrayList<Rating> ratings = fourthRatings.getAverageRatingsByFilter(minRaters,f);

        System.out.println("Found "+ratings.size()+" Movies");

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
                String title = MovieDatabase.getTitle(rate.getItem());
                double avgRate = rate.getValue();

                System.out.println(title + " " +rate.getValue());
                System.out.println(MovieDatabase.getYear(rate.getItem()));
                System.out.println(MovieDatabase.getGenres(rate.getItem()));
                System.out.println();
            }
        }
    }

    public void printSimilarRatings(){
        FourthRatings fourthRatings = new FourthRatings();

        String id = "71";

        ArrayList<Rating> ratings = fourthRatings.getSimilarRatings(id, 20,5);

        for (Rating rate:
             ratings) {
            System.out.println(MovieDatabase.getTitle(rate.getItem())+" "+rate.getValue());
        }
    }

    public void printSimilarRatingsByGenre(){
        FourthRatings fourthRatings = new FourthRatings();

        String id = "964";
        int numSimilarRaters = 20;
        int minRaters = 5;
        Filter f = new GenreFilter("Mystery");

        ArrayList<Rating> ratings = fourthRatings.getSimilarRatingsByFilter(id, numSimilarRaters,minRaters,f);

        for (Rating rate:
                ratings) {
            System.out.println(MovieDatabase.getTitle(rate.getItem())+" "+rate.getValue()+" "+MovieDatabase.getGenres(rate.getItem()));
        }
    }

    public void printSimilarRatingsByDirector(){
        FourthRatings fourthRatings = new FourthRatings();

        String id = "120";
        int numSimilarRaters = 10;
        int minRaters = 2;
        Filter f = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");

        ArrayList<Rating> ratings = fourthRatings.getSimilarRatingsByFilter(id, numSimilarRaters,minRaters,f);

        for (Rating rate:
                ratings) {
            System.out.println(MovieDatabase.getTitle(rate.getItem())+" "+rate.getValue());
        }
    }

    public void printSimilarRatingsByGenreAndMinutes (){
        FourthRatings fourthRatings = new FourthRatings();

        String id = "168";
        int numSimilarRaters = 10;
        int minRaters = 3;
        Filter f = new AllFilters();
        ((AllFilters)f).addFilter(new GenreFilter("Drama"));
        ((AllFilters)f).addFilter(new MinutesFilter(80,160));

        ArrayList<Rating> ratings = fourthRatings.getSimilarRatingsByFilter(id, numSimilarRaters,minRaters,f);

        for (Rating rate:
                ratings) {
            System.out.println(MovieDatabase.getTitle(rate.getItem())+" "+rate.getValue());
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes (){
        FourthRatings fourthRatings = new FourthRatings();

        String id = "314";
        int numSimilarRaters = 10;
        int minRaters = 5;
        Filter f = new AllFilters();
        ((AllFilters)f).addFilter(new YearAfterFilter(1975));
        ((AllFilters)f).addFilter(new MinutesFilter(70,200));

        ArrayList<Rating> ratings = fourthRatings.getSimilarRatingsByFilter(id, numSimilarRaters,minRaters,f);

        for (Rating rate:
                ratings) {
            System.out.println(MovieDatabase.getTitle(rate.getItem())+" "+rate.getValue());
        }
    }
}
