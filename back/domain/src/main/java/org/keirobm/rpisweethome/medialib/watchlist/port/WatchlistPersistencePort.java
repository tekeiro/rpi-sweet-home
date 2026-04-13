package org.keirobm.rpisweethome.medialib.watchlist.port;

import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;

import java.util.List;
import java.util.Optional;

public interface WatchlistPersistencePort {
    WatchlistItem addToWatchlist(WatchlistItem watchlistItem);
    List<WatchlistItem> getWatchlistItems();
    Optional<WatchlistItem> getItemById(WatchlistItemType type, Long id);
    WatchlistItem persist(WatchlistItem watchlistItem);
}
