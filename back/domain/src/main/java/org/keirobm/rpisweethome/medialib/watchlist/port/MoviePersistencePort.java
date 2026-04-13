package org.keirobm.rpisweethome.medialib.watchlist.port;

import org.keirobm.rpisweethome.medialib.watchlist.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MoviePersistencePort {
    List<Movie> getAll();
    Optional<Movie> getById(Long id);
    Movie persist(Movie movie);
}
