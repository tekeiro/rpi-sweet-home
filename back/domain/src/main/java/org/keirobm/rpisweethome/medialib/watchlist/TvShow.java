package org.keirobm.rpisweethome.medialib.watchlist;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
public class TvShow extends WatchlistItem {
    private final OnAirStatus onAir;

    @Builder.Default
    private final List<Season> seasons = new ArrayList<>();
}
