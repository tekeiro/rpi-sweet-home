package org.keirobm.rpisweethome.medialib.watchlist;

import lombok.Builder;
import lombok.Data;
import org.keirobm.rpisweethome.common.events.EventBus;
import org.keirobm.rpisweethome.medialib.watchlist.events.TvShowEpisodeToDownloadEvent;

@Data
@Builder(toBuilder = true)
public class Episode {
    private transient final Season season;

    private Integer number;
    private String title;
    private String externalId;
    private boolean watched;
    private boolean toDownload;

    public void setToDownload(boolean toDownload) {
        this.toDownload = toDownload;
        if (toDownload) {
            EventBus.publish(new TvShowEpisodeToDownloadEvent(this.getSeason().getTvShow(),
                    this.getSeason(), this));
        }
    }
}
