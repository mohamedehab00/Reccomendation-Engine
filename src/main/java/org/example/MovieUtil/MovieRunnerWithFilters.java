package org.example.MovieUtil;

import org.example.Filters.*;
import org.example.RateUtil.Rating;
import org.example.ReccomendationEngine.ThirdRatings;

import java.util.ArrayList;
import java.util.Comparator;

public class MovieRunnerWithFilters{
    public void printAverageRatings(int minRaters){
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ThirdRatings thirdRatings = new ThirdRatings();

        System.out.println("Number Of Movies Loaded : "+movies.size()+" - Number Of Raters : "+thirdRatings.getRaterSize());

        ArrayList<Rating> ratings = thirdRatings.getAverageRatings(minRaters);
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

    public void printAverageRatingsByYear(int minRaters, int year){
        Filter f = new YearAfterFilter(year);
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ThirdRatings thirdRatings = new ThirdRatings();
        System.out.println("Number Of Movies Loaded : "+movies.size()+" - Number Of Raters : "+thirdRatings.getRaterSize());

        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(minRaters,f);

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
            }
        }
    }

    public void printAverageRatingsByGenre(int minRaters, String genere){
        Filter f = new GenreFilter(genere);
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ThirdRatings thirdRatings = new ThirdRatings();
        System.out.println("Number Of Movies Loaded : "+movies.size()+" - Number Of Raters : "+thirdRatings.getRaterSize());

        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(minRaters,f);

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
                System.out.println(MovieDatabase.getGenres(rate.getItem()));
                System.out.println();
            }
        }
    }

    public void printAverageRatingsByMinutes(int minRaters, int min, int max){
        Filter f = new MinutesFilter(min, max);
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ThirdRatings thirdRatings = new ThirdRatings();
        System.out.println("Number Of Movies Loaded : "+movies.size()+" - Number Of Raters : "+thirdRatings.getRaterSize());

        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(minRaters,f);

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
                System.out.println(MovieDatabase.getMinutes(rate.getItem()));
                System.out.println();
            }
        }
    }

    public void printAverageRatingsByDirectors(int minRaters, String directors){
        Filter f = new DirectorsFilter(directors);
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ThirdRatings thirdRatings = new ThirdRatings();
        System.out.println("Number Of Movies Loaded : "+movies.size()+" - Number Of Raters : "+thirdRatings.getRaterSize());

        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(minRaters,f);

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
                System.out.println(MovieDatabase.getDirector(rate.getItem()));
                System.out.println();
            }
        }
    }

    public void printAverageRatingsByYearAfterAndGenre(int minRaters, int year, String genere){
        Filter f = new AllFilters();
        ((AllFilters) f).addFilter(new YearAfterFilter(year));
        ((AllFilters) f).addFilter(new GenreFilter(genere));

        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ThirdRatings thirdRatings = new ThirdRatings();
        System.out.println("Number Of Movies Loaded : "+movies.size()+" - Number Of Raters : "+thirdRatings.getRaterSize());

        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(minRaters,f);

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

    public void printAverageRatingsByDirectorsAndMinutes(int minRaters,String directors,int min,int max){
        Filter f = new AllFilters();
        ((AllFilters) f).addFilter(new DirectorsFilter(directors));
        ((AllFilters) f).addFilter(new MinutesFilter(min,max));

        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ThirdRatings thirdRatings = new ThirdRatings();
        System.out.println("Number Of Movies Loaded : "+movies.size()+" - Number Of Raters : "+thirdRatings.getRaterSize());

        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(minRaters,f);

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
                System.out.println(MovieDatabase.getMinutes(rate.getItem()));
                System.out.println(MovieDatabase.getDirector(rate.getItem()));
                System.out.println();
            }
        }
    }
}
