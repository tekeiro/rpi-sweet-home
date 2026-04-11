package org.keirobm.rpisweethome.medialib.watchlist.port;

import org.keirobm.rpisweethome.medialib.watchlist.input.SearchRefinement;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;

import java.util.List;
import java.util.Optional;

/**
 * A port to search films or tv show in a provider like TVDB.
 */
public interface SearchTvProviderPort {
    List<WatchlistItem> search(String query, Optional<SearchRefinement> searchRefinement);
}
