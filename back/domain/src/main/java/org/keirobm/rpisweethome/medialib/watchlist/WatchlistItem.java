package org.keirobm.rpisweethome.medialib.watchlist;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public abstract class WatchlistItem {
    private final Long id;
    private final String title;
    private final Integer year;
    private final String overview;
    private final String imageUrl;
    private final List<String> genres;
    private final String externalId;
}
