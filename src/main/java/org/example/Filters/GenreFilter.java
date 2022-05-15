package org.example.Filters;

import org.example.MovieUtil.MovieDatabase;

public class GenreFilter implements Filter {
    private String genere;

    public GenreFilter(String genere) {
        this.genere = genere;
    }
    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getGenres(id).contains(this.genere);
    }
}
