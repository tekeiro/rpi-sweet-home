package org.keirobm.rpisweethome.medialib.watchlist.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
public abstract class WatchlistItem {
    protected Long id;
    protected String title;
    protected Integer year;
    protected String overview;
    protected String imageUrl;
    protected List<String> genres;
    protected String externalId;
    protected boolean watched;
    protected boolean toDownload;

    protected WatchlistItem() {
    }
}
