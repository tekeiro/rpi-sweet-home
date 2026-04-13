package org.keirobm.rpisweethome.medialib.search.model;

import lombok.Builder;
import lombok.Data;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class LinkSearchResult {
    private String fullTitle;
    private String title;
    private String providerKey;
    private WatchlistItemType contentType;
    private List<QualityTag> tags;
    private WatchlistItem item;
    private Integer seasonOrNull;
    private Integer fromEpisode;
    private Integer toEpisode;
    private String torrentUrl;
    private Double score;
}
