package org.example.Filters;

import org.example.MovieUtil.MovieDatabase;

public class DirectorsFilter implements Filter{
    private String[] director;

    public DirectorsFilter(String director) {
        this.director = director.split(",");
    }

    @Override
    public boolean satisfies(String id) {
        for (int i = 0; i < this.director.length; i++) {
            if(MovieDatabase.getDirector(id).contains(this.director[i])){
                return true;
            }
        }
        return false;
    }
}
