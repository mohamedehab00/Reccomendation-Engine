package org.example.Filters;

import org.example.MovieUtil.MovieDatabase;

public class MinutesFilter implements Filter {
    private int minMinutes,maxMinutes;

    public MinutesFilter(int min,int max) {
        this.minMinutes = min;
        this.maxMinutes = max;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getMinutes(id) >= minMinutes && MovieDatabase.getMinutes(id) <= maxMinutes;
    }
}
