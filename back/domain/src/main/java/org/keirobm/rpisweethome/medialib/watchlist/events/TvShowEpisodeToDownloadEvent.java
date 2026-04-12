package org.keirobm.rpisweethome.medialib.watchlist.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.keirobm.rpisweethome.common.events.BaseEvent;
import org.keirobm.rpisweethome.medialib.watchlist.model.Episode;
import org.keirobm.rpisweethome.medialib.watchlist.model.Season;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;

@Data
@EqualsAndHashCode(callSuper = true)
public class TvShowEpisodeToDownloadEvent extends BaseEvent {
    private final TvShow tvShow;
    private final Season season;
    private final Episode episode;

    public String toString() {
        return String.format("TvShowEpisodeToDownloadEvent{ '%s(%d)' %dx%d }",
                tvShow.getTitle(), tvShow.getYear(), season.getNumber(), episode.getNumber());
    }
}
