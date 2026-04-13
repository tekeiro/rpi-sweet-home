package org.keirobm.rpisweethome.medialib.watchlist.model;

import lombok.Builder;
import lombok.Data;
import org.keirobm.rpisweethome.common.events.EventBus;
import org.keirobm.rpisweethome.medialib.watchlist.events.TvShowEpisodeToDownloadEvent;

import java.util.Objects;

@Data
@Builder(toBuilder = true)
public class Episode {
    private transient final Season season;

    private Long id;
    private Integer number;
    private String title;
    private String externalId;
    private String overview;
    private String imageUrl;
    private boolean watched;
    private boolean toDownload;

    public void setToDownload(boolean toDownload) {
        this.toDownload = toDownload;
        if (toDownload) {
            EventBus.publish(new TvShowEpisodeToDownloadEvent(this.getSeason().getTvShow(),
                    this.getSeason(), this));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Episode episode = (Episode) o;
        return watched == episode.watched && toDownload == episode.toDownload
                && Objects.equals(id, episode.id) && Objects.equals(number, episode.number)
                && Objects.equals(title, episode.title) && Objects.equals(externalId, episode.externalId)
                && Objects.equals(overview, episode.overview) && Objects.equals(imageUrl, episode.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, title, externalId, overview, imageUrl, watched, toDownload);
    }

    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", number=" + number +
                ", title='" + title + '\'' +
                ", externalId='" + externalId + '\'' +
                ", overview='" + overview + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", watched=" + watched +
                ", toDownload=" + toDownload +
                '}';
    }
}
