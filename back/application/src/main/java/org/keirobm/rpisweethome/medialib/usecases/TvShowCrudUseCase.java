package org.keirobm.rpisweethome.medialib.usecases;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.watchlist.input.UpdateWatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;
import org.keirobm.rpisweethome.medialib.watchlist.port.TvShowPersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TvShowCrudUseCase {

    private final TvShowPersistencePort tvShowPersistencePort;
    private final AddToWatchlistUseCase addToWatchlistUseCase;

    public List<TvShow> getAllTvShows() {
        return this.tvShowPersistencePort.getAll();
    }

    public TvShow addTvShow(String externalId) {
        final var item = this.addToWatchlistUseCase.addToWatchlist(WatchlistItemType.TV_SHOW, externalId);
        return (TvShow) item;
    }

    public Optional<TvShow> getTvShowById(Long id) {
        return this.tvShowPersistencePort.getById(id);
    }

    public TvShow updateTvShow(Long id, UpdateWatchlistItem request) {
        return this.tvShowPersistencePort.getById(id).map(tvShow -> {
            if (request.getMarkToDownload() != null) {
                tvShow.setToDownload(request.getMarkToDownload());
            }
            if (request.getMarkToWatch() != null) {
                tvShow.setWatched(request.getMarkToWatch());
            }
            return this.tvShowPersistencePort.persist(tvShow);
        }).orElse(null);
    }

}
