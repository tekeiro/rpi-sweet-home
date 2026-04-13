package org.keirobm.rpisweethome.adapter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.entities.MovieEntity;
import org.keirobm.rpisweethome.entities.TvShowEntity;
import org.keirobm.rpisweethome.mapper.EpisodeDataMapper;
import org.keirobm.rpisweethome.mapper.MovieDataMapper;
import org.keirobm.rpisweethome.mapper.SeasonDataMapper;
import org.keirobm.rpisweethome.mapper.TvShowDataMapper;
import org.keirobm.rpisweethome.medialib.watchlist.model.*;
import org.keirobm.rpisweethome.medialib.watchlist.port.WatchlistPersistencePort;
import org.keirobm.rpisweethome.repositories.EpisodeRepository;
import org.keirobm.rpisweethome.repositories.MovieRepository;
import org.keirobm.rpisweethome.repositories.SeasonRepository;
import org.keirobm.rpisweethome.repositories.TvShowRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class WatchlistPersistenceAdapter implements WatchlistPersistencePort {

    private final TvShowPersistenceAdapter tvShowPersistenceAdapter;
    private final MoviePersistenceAdapter moviePersistenceAdapter;


    @Transactional
    @Override
    public WatchlistItem addToWatchlist(WatchlistItem item) {
        return switch (item.getType()) {
            case MOVIE -> this.moviePersistenceAdapter.addMovieToWatchlist((Movie) item);
            case TV_SHOW -> this.tvShowPersistenceAdapter.addTvShowToWatchlist((TvShow) item);
            case null, default -> throw new IllegalArgumentException("Unsupported type: " + item.getType());
        };
    }


    @Transactional(readOnly = true)
    @Override
    public List<WatchlistItem> getWatchlistItems() {
        final List<WatchlistItem> items = new ArrayList<>();
        items.addAll(this.moviePersistenceAdapter.getAll());
        items.addAll(this.tvShowPersistenceAdapter.getAll());
        return items;
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<WatchlistItem> getItemById(WatchlistItemType type, Long id) {
        return switch (type) {
            case MOVIE:
                yield this.moviePersistenceAdapter.getById(id)
                        .map(i -> (WatchlistItem) i);
            case TV_SHOW:
                yield this.tvShowPersistenceAdapter.getById(id)
                        .map(i -> (WatchlistItem) i);
            default:
                yield Optional.empty();
        };
    }

    @Transactional
    @Override
    public WatchlistItem persist(WatchlistItem item) {
        if (item instanceof Movie movie) {
            return this.moviePersistenceAdapter.persist(movie);
        }
        else if (item instanceof TvShow tvShow) {
            return this.tvShowPersistenceAdapter.persist(tvShow);
        }
        return null;
    }

}
