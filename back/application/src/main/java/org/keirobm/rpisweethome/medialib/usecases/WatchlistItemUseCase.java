package org.keirobm.rpisweethome.medialib.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.medialib.watchlist.input.UpdateWatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.port.WatchlistPersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class WatchlistItemUseCase {

    private final WatchlistPersistencePort watchlistPersistencePort;

    public List<WatchlistItem> getItems() {
        return this.watchlistPersistencePort.getWatchlistItems();
    }

    public Optional<WatchlistItem> getWatchlistItemById(Long id) {
        return this.watchlistPersistencePort.getItemById(id);
    }

    public Optional<WatchlistItem> updateWatchlistItem(Long id, UpdateWatchlistItem request) {
        final var itemOpt = this.watchlistPersistencePort.getItemById(id);
        return itemOpt.map(watchlistItem -> {
            if (request.getMarkToDownload() != null) {
                watchlistItem.setToDownload(request.getMarkToDownload());
            }
            if (request.getMarkToWatch() != null) {
                watchlistItem.setWatched(request.getMarkToWatch());
            }
            return this.watchlistPersistencePort.persist(watchlistItem);
        });
    }

}
