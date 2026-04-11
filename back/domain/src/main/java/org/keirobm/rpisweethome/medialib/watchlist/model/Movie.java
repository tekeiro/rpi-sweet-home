package org.keirobm.rpisweethome.medialib.watchlist.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.keirobm.rpisweethome.common.events.EventBus;
import org.keirobm.rpisweethome.medialib.watchlist.events.MovieToDownloadEvent;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Movie extends WatchlistItem {

    public Movie() {
        super();
    }

    public void setToDownload(boolean toDownload) {
        this.toDownload = toDownload;
        if (toDownload)
            EventBus.publish(new MovieToDownloadEvent(this));
    }

}
