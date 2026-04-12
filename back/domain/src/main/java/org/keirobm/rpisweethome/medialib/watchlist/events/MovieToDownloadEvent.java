package org.keirobm.rpisweethome.medialib.watchlist.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.keirobm.rpisweethome.common.events.BaseEvent;
import org.keirobm.rpisweethome.medialib.watchlist.model.Movie;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieToDownloadEvent extends BaseEvent {
    private final Movie movie;

    public String toString() {
        return String.format("MovieToDownloadEvent{ '%s(%d)' }", movie.getTitle(), movie.getYear());
    }
}
