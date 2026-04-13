package org.keirobm.rpisweethome.medialib.usecases;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.watchlist.input.UpdateWatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.Movie;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;
import org.keirobm.rpisweethome.medialib.watchlist.port.MoviePersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MoviesCrudUseCase {

    private final MoviePersistencePort moviePersistencePort;
    private final AddToWatchlistUseCase addToWatchlistUseCase;

    public List<Movie> getAllMovies() {
        return this.moviePersistencePort.getAll();
    }

    public Movie addMovie(String externalId) {
        final var item = this.addToWatchlistUseCase.addToWatchlist(WatchlistItemType.MOVIE, externalId);
        return (Movie) item;
    }

    public Optional<Movie> getMovieById(Long id) {
        return this.moviePersistencePort.getById(id);
    }

    public Movie updateMovie(Long id, UpdateWatchlistItem request) {
        return this.moviePersistencePort.getById(id).map(movie -> {
            if (request.getMarkToDownload() != null) {
                movie.setToDownload(request.getMarkToDownload());
            }
            if (request.getMarkToWatch() != null) {
                movie.setWatched(request.getMarkToWatch());
            }
            return this.moviePersistencePort.persist(movie);
        }).orElse(null);
    }



}
