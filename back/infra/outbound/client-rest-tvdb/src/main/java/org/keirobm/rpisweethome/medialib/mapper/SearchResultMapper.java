package org.keirobm.rpisweethome.medialib.mapper;

import com.tvdb.v4.model.SearchResult;
import org.keirobm.rpisweethome.medialib.watchlist.model.Movie;
import org.keirobm.rpisweethome.medialib.watchlist.model.OnAirStatus;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.thirdparty.TvdbLangs;
import org.springframework.stereotype.Component;

@Component
public class SearchResultMapper {

    public WatchlistItem fromSearchResult(SearchResult searchResult) {
        final var externalId = searchResult.getTvdbId();

        boolean isMovie = false;
        boolean isTvShow = false;
        switch (searchResult.getType()) {
            case "movie":
                isMovie = true;
                break;
            case "series":
                isTvShow = true;
                break;
            default:
                break;
        }
        if (!isMovie && !isTvShow) {
            return null;
        }


        final var name = searchResult.getName();
        final var year = (searchResult.getYear() != null && !searchResult.getYear().isBlank())
                ? Integer.parseInt(searchResult.getYear()) : null;

        final var defaultOverview = searchResult.getOverviews()
                .getOrDefault(TvdbLangs.ENG.getLangCode(), searchResult.getOverview());
        final var overview = searchResult.getOverviews().getOrDefault(TvdbLangs.SPA.getLangCode(), defaultOverview);
        final var imageUrl = searchResult.getThumbnail();
        final var genres = searchResult.getGenres();

        OnAirStatus onAir = null;
        switch (searchResult.getStatus()) {
            case "Continuing":
                onAir = OnAirStatus.ON_AIR;
                break;
            case "Ended":
                onAir = OnAirStatus.FINISHED;
                break;
            case null, default:
                onAir = OnAirStatus.FINISHED;
                break;
        }

        if (isMovie) {
            return Movie.builder()
                    .title(name)
                    .year(year)
                    .overview(overview)
                    .imageUrl(imageUrl)
                    .genres(genres)
                    .externalId(externalId)
                    .watched(false)
                    .toDownload(false)
                    .build();
        } else if (isTvShow) {
            return TvShow.builder()
                    .title(name)
                    .year(year)
                    .overview(overview)
                    .imageUrl(imageUrl)
                    .genres(genres)
                    .externalId(externalId)
                    .watched(false)
                    .toDownload(false)
                    .onAir(onAir)
                    .build();
        } else {
            return null;
        }

    }

}
