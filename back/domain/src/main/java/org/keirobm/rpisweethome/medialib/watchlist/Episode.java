package org.keirobm.rpisweethome.medialib.watchlist;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Episode {
    private final Integer number;
    private final String title;
    private final String externalId;
}
