package org.keirobm.rpisweethome.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;

@Getter
@RequiredArgsConstructor
public enum MejorTorrentLinkType {
    FILM("peliculas", WatchlistItemType.MOVIE),
    DOCUMENTAL("documentales", null),
    SERIES("series", WatchlistItemType.TV_SHOW),

    ;

    private final String key;
    private final WatchlistItemType contentType;

    public static MejorTorrentLinkType fromKey(String key) {
        for (MejorTorrentLinkType type : values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid key: " + key);
    }
}
