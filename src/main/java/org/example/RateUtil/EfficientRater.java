package org.example.RateUtil;

import org.example.RateUtil.Rater;
import org.example.RateUtil.Rating;

import java.util.ArrayList;
import java.util.HashMap;

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }

    public void addRating(String item, double rating) {
        Rating rate = new Rating(item,rating);
        myRatings.put(item, rate);
    }

    public boolean hasRating(String item) {
        return myRatings.containsKey(item);
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        return myRatings.getOrDefault(item,new Rating("",-1)).getValue();
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for (String currMovie:
             myRatings.keySet()) {
            list.add(currMovie);
        }
        return list;
    }
}
