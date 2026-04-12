package org.keirobm.rpisweethome.medialib.dto;

import lombok.Data;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;

@Data
public class AddToWatchlistRequest {
    private WatchlistItemType type;
    private String externalId;
}
