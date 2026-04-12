package org.keirobm.rpisweethome.medialib.watchlist.port;

import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;

import java.util.List;

public interface WatchlistPersistencePort {
    WatchlistItem addToWatchlist(WatchlistItem watchlistItem);
    List<WatchlistItem> getWatchlistItems();
}
