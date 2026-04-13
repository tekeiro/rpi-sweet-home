package org.keirobm.rpisweethome.medialib.watchlist.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Builder(toBuilder = true)
public class Season {
    private transient final TvShow tvShow;

    private Long id;
    private Integer number;
    private String externalId;
    private String imageUrl;
    private boolean watched;
    private boolean toDownload;

    @Builder.Default
    private List<Episode> episodes = new ArrayList<>();

    public void setToDownload(boolean toDownload) {
        this.toDownload = toDownload;
        Optional.ofNullable(this.episodes).orElse(List.of())
                .forEach(episode -> episode.setToDownload(toDownload));
    }

    public Optional<Episode> getEpisodeByNumber(int episodeNumber) {
        return this.episodes.stream().filter(episode -> episode.getNumber() == episodeNumber).findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return watched == season.watched
                && toDownload == season.toDownload
                && Objects.equals(id, season.id)
                && Objects.equals(number, season.number)
                && Objects.equals(externalId, season.externalId)
                && Objects.equals(imageUrl, season.imageUrl) && Objects.equals(episodes, season.episodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, externalId, imageUrl, watched, toDownload, episodes);
    }

    public String toString() {
        return "Season{" +
                "id=" + id +
                ", number=" + number +
                ", externalId='" + externalId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", watched=" + watched +
                ", toDownload=" + toDownload +
                '}';
    }

}
