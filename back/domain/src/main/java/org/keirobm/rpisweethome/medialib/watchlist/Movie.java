package org.keirobm.rpisweethome.medialib.watchlist;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
public class Movie extends WatchlistItem {
}
