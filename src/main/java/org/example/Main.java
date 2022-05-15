package org.example;

import org.example.ReccomendationEngine.RecommendationRunner;

public class Main {
    public static void main(String[] args) {
        RecommendationRunner recommendationRunner = new RecommendationRunner();
        recommendationRunner.printRecommendationsFor("1");
    }
}