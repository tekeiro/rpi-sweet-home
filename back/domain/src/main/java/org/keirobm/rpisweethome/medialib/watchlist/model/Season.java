package org.keirobm.rpisweethome.medialib.watchlist.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder(toBuilder = true)
public class Season {
    private transient final TvShow tvShow;

    private Integer number;
    private String externalId;
    private boolean watched;
    private boolean toDownload;

    @Builder.Default
    private List<Episode> episodes = new ArrayList<>();

    public void setToDownload(boolean toDownload) {
        this.toDownload = toDownload;
        Optional.ofNullable(this.episodes).orElse(List.of())
                .forEach(episode -> episode.setToDownload(toDownload));
    }
}
