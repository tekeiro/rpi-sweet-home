package org.keirobm.rpisweethome.medialib.watchlist;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.keirobm.rpisweethome.common.events.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
public class TvShow extends WatchlistItem {
    private OnAirStatus onAir;

    @Builder.Default
    private List<Season> seasons = new ArrayList<>();

    public void setToDownload(boolean toDownload) {
        this.toDownload = toDownload;
        Optional.ofNullable(this.seasons).orElse(List.of()).forEach(season -> season.setToDownload(toDownload));
    }
}
