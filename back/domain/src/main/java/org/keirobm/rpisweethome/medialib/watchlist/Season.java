package org.keirobm.rpisweethome.medialib.watchlist;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Season {
    private final Integer number;
    private final String externalId;

    @Builder.Default
    private final List<Episode> episodes = new ArrayList<>();
}
