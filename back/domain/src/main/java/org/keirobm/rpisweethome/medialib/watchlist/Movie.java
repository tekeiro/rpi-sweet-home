package org.keirobm.rpisweethome.medialib.watchlist;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.keirobm.rpisweethome.common.events.EventBus;
import org.keirobm.rpisweethome.medialib.watchlist.events.MovieToDownloadEvent;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
public class Movie extends WatchlistItem {

    public void setToDownload(boolean toDownload) {
        this.toDownload = toDownload;
        if (toDownload)
            EventBus.publish(new MovieToDownloadEvent(this));
    }

}
