package org.keirobm.rpisweethome.medialib.watchlist.port;

import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;

public interface WatchlistPersistencePort {
    WatchlistItem addToWatchlist(WatchlistItem watchlistItem);
}
