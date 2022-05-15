package org.example.ReccomendationEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.commons.csv.*;
import org.example.RateUtil.EfficientRater;
import org.example.MovieUtil.Movie;
import org.example.RateUtil.Rater;
import org.example.RateUtil.RaterDatabase;

public class FirstRatings {
    public ArrayList<Movie> loadMovies(String fileName){
        ArrayList<Movie> fileRecords = new ArrayList<>();

        try {
            File csvData = new File(fileName);
            CSVParser csvParser = CSVParser.parse(csvData, StandardCharsets.UTF_8,CSVFormat.EXCEL);

            Iterator<CSVRecord> it = csvParser.iterator();
            CSVRecord record = it.next();

            for (; it.hasNext(); ) {
                record = it.next();

                String id = record.get(0);
                String title = record.get(1);
                String year = record.get(2);
                String genres = record.get(4);
                String director = record.get(5);
                String country = record.get(3);
                String poster = record.get(7);
                int minutes = Integer.parseInt(record.get(6));

                Movie currMovie = new Movie(id,title,year,genres,director,country,poster,minutes);

                //System.out.println(currMovie);

                fileRecords.add(currMovie);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return fileRecords;
    }

    private int numOfRecordsContainsGenere(ArrayList<Movie> records ,String genere){
        int ans = 0;
        for (Movie currMovie:
             records) {
            if (currMovie.getGenres().contains(genere)){
                ans+=1;
            }
        }
        return ans;
    }

    private int numOfMovieMinutesGreaterThan(ArrayList<Movie> records ,int limit){
        int ans = 0;
        for (Movie currMovie:
                records) {
            if (currMovie.getMinutes() > limit){
                ans+=1;
            }
        }
        return ans;
    }

    private HashMap<String, Integer> numOfMoviesPerDirector(ArrayList<Movie> records){
        HashMap<String, Integer> numOfMoviesPerDirectorsTable = new HashMap<>();
        for (Movie currMovie:
             records) {
            String[] currMovieDirectors = currMovie.getDirector().split(",");

            for (int i = 0; i < currMovieDirectors.length; i++) {

                String currDirector = currMovieDirectors[i].trim();

                if (numOfMoviesPerDirectorsTable.containsKey(currDirector)){
                    int value = numOfMoviesPerDirectorsTable.get(currDirector);
                    numOfMoviesPerDirectorsTable.put(currDirector,value+1);
                }
                else {
                    numOfMoviesPerDirectorsTable.put(currDirector,1);
                }
            }
        }
        return numOfMoviesPerDirectorsTable;
    }

    private int numOfMoviesDirectedByDirector(HashMap<String, Integer> directorsTable, String director){
        return directorsTable.getOrDefault(director,0);
    }

    private int maxNumOfMoviesByDirector(HashMap<String, Integer> directorsTable){
        int maxNumOfMovies = Integer.MIN_VALUE;

        for (String director:
             directorsTable.keySet()) {
            Integer totalMovies = directorsTable.get(director);

            maxNumOfMovies = Math.max(maxNumOfMovies, totalMovies);
        }

        return maxNumOfMovies;
    }

    private ArrayList<String> directorsWithMaxNumOfMovies(HashMap<String ,Integer> directorsTable, int maxNumOfMovies) {
        ArrayList<String> directors = new ArrayList<>();

        for (String director:
                directorsTable.keySet()) {
            if (directorsTable.get(director) == maxNumOfMovies){
                directors.add(director);
            }
        }
        return directors;
    }


    public HashMap<String, Rater> loadRaters(String fileName){
        HashMap<String, Rater> raters = new HashMap<>();

        try {
            File csvData = new File(fileName);
            CSVParser csvParser = CSVParser.parse(csvData, StandardCharsets.UTF_8,CSVFormat.EXCEL);

            Iterator<CSVRecord> it = csvParser.iterator();
            CSVRecord record = it.next();

            for (; it.hasNext(); ) {
                record = it.next();

                String raterId = record.get(0);

                if (raters.containsKey(raterId)){
                    raters.get(raterId).addRating(record.get(1), Double.parseDouble(record.get(2)));
                }
                else {
                    Rater curr = new EfficientRater(raterId);
                    curr.addRating(record.get(1), Double.parseDouble(record.get(2)));
                    raters.put(raterId,curr);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return raters;
    }

    private void printRaters(HashMap<String, EfficientRater> raters){
        for (String raterID:
             raters.keySet()) {
            EfficientRater currRater = raters.get(raterID);
            System.out.println("Rater ID : "+raterID+" - Num of Ratings "+currRater.numRatings());
            
            ArrayList<String> itemsRated = currRater.getItemsRated();

            for (String item:
                 itemsRated) {
                System.out.println("Item Id : "+item+" - Rating : "+currRater.getRating(item));
            }
            System.out.println();
        }
    }

    private int numOfRatingsForParticularRater(HashMap<String, Rater> raterHashMap, String raterId){
        return raterHashMap.getOrDefault(raterId,new EfficientRater(raterId)).numRatings();
    }

    private int maxNumOfRatingsByRater(HashMap<String, Rater> raterHashMap){
        int maxNumOfRatings = Integer.MIN_VALUE;

        for (String raterId:
                raterHashMap.keySet()) {
            Integer totalRtaings = raterHashMap.get(raterId).numRatings();

            maxNumOfRatings = Math.max(maxNumOfRatings, totalRtaings);
        }

        return maxNumOfRatings;
    }

    private ArrayList<String> numOfRatersWithMaxNumOfRatings(HashMap<String, Rater> raterHashMap, int maxNumOfRatingsPerRater) {
        ArrayList<String> raters = new ArrayList<>();

        for (String raterId:
                raterHashMap.keySet()) {
            if (raterHashMap.get(raterId).numRatings() == maxNumOfRatingsPerRater){
                raters.add(raterId);
            }
        }
        return raters;
    }

    public double[] ratingsParticularMovieHas(ArrayList<Rater> raters, String item){
        double[] rateInfo = {0,0};
        for (Rater currRater:
             raters) {

            if (currRater.hasRating(item)){
                rateInfo[0] += 1;
                rateInfo[1] += currRater.getRating(item);
            }
        }
        return rateInfo;
    }

    private HashSet<String> getListOfAllMovies(String fileName){
        HashSet<String> movies = new HashSet<>();

        try {
            File csvData = new File(fileName);
            CSVParser csvParser = CSVParser.parse(csvData, StandardCharsets.UTF_8,CSVFormat.EXCEL);

            Iterator<CSVRecord> it = csvParser.iterator();
            CSVRecord record = it.next();

            for (; it.hasNext(); ) {
                record = it.next();

                String movie = record.get(1);

                movies.add(movie);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return movies;
    }

    public void testLoadMovies(){
        String fileName = "data/ratedmovies_short.csv";

        ArrayList<Movie> records = loadMovies(fileName);

        System.out.println("Number Of Movies : "+records.size());

        String genere = "Comedy";

        System.out.println("Number of records contains "+genere+" : "+numOfRecordsContainsGenere(records, genere));

        int minLimit = 150;

        System.out.println("Number of Movie greater than "+minLimit+" : "+numOfMovieMinutesGreaterThan(records, minLimit));

        HashMap<String ,Integer> directorsTable = numOfMoviesPerDirector(records);
        int maxNumOfMoviesDirected = maxNumOfMoviesByDirector(directorsTable);
        ArrayList<String> directorsThatDirectedMaxNumMovies = directorsWithMaxNumOfMovies(directorsTable,maxNumOfMoviesDirected);

        System.out.println("Max Number of Movies directed by a one director : "+maxNumOfMoviesDirected);
        System.out.println("Directors directed max num of movies : "+directorsThatDirectedMaxNumMovies);
        System.out.println("Number of directors directed max num of movies : "+directorsThatDirectedMaxNumMovies.size());
    }

    public void testLoadRaters(){
        String fileName = "data/ratings_short.csv";

        HashMap<String, Rater> raterHashMap = loadRaters(fileName);

        System.out.println("Total number of raters : "+raterHashMap.size());

        //printRaters(raterHashMap);

        String raterId = "193";

        System.out.println("Number of ratings of rater with id = "+raterId+" : "+numOfRatingsForParticularRater(raterHashMap,raterId));

        int maxNumOfRatingsPerRater = maxNumOfRatingsByRater(raterHashMap);
        ArrayList<String > ratersThatHaveMaxNumOfRatings = numOfRatersWithMaxNumOfRatings(raterHashMap, maxNumOfRatingsPerRater);

        System.out.println("Max Number of Ratings Per Rater : "+maxNumOfRatingsPerRater);
        System.out.println("Raters with Max Ratings : "+ratersThatHaveMaxNumOfRatings);
        System.out.println("Number of Raters with Max Ratings : "+ratersThatHaveMaxNumOfRatings.size());

        String movieId = "1798709";
        int numOfRatings = (int) ratingsParticularMovieHas(RaterDatabase.getRaters(),movieId)[0];
        System.out.println("Number of ratings of movie with id = "+movieId+" : "+numOfRatings);

        HashSet<String> allMoviesInCSV = getListOfAllMovies(fileName);
        System.out.println("Number of different movies have been rated by all these raters : "+allMoviesInCSV.size());
    }
}
