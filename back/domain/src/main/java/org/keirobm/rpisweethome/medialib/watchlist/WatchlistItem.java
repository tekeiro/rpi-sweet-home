package org.keirobm.rpisweethome.medialib.watchlist;

import lombok.Builder;
import lombok.Data;
import org.keirobm.rpisweethome.common.events.EventBus;

import java.util.List;

@Data
@Builder(toBuilder = true)
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
}
