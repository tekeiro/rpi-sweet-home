package org.keirobm.rpisweethome.medialib.watchlist.input;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UpdateWatchlistItem {
    private Boolean markToDownload;
    private Boolean markToWatch;
}
