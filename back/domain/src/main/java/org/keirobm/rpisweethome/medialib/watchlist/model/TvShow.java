package org.keirobm.rpisweethome.medialib.watchlist.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TvShow extends WatchlistItem {
    private OnAirStatus onAir;

    @Builder.Default
    private List<Season> seasons = new ArrayList<>();

    public TvShow() {
        super();
    }

    @Override
    public WatchlistItemType getType() {
        return WatchlistItemType.TV_SHOW;
    }

    public void setToDownload(boolean toDownload) {
        this.toDownload = toDownload;
        Optional.ofNullable(this.seasons).orElse(List.of()).forEach(season -> season.setToDownload(toDownload));
    }

    public Optional<Season> getSeasonByNumber(int seasonNumber) {
        return this.seasons.stream().filter(season -> season.getNumber() == seasonNumber).findFirst();
    }
}
