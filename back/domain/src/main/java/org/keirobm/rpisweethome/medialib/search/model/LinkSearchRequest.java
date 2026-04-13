package org.keirobm.rpisweethome.medialib.search.model;

import lombok.Builder;
import lombok.Data;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;

/**
 * A request can be a movie or a tv show.
 * If it's for a tv show, season and episode must be provided.
 */
@Data
@Builder(toBuilder = true)
public class LinkSearchRequest {
    private WatchlistItem item;
    private Integer season;
    private Integer episode;
}
