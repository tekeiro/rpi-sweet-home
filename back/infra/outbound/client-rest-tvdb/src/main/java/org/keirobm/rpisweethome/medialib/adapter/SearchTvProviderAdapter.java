package org.keirobm.rpisweethome.medialib.adapter;

import com.tvdb.v4.api.LoginApi;
import com.tvdb.v4.api.SearchApi;
import com.tvdb.v4.model.GetEpisodeTranslation200Response;
import com.tvdb.v4.model.Translation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.medialib.TvdbApi;
import org.keirobm.rpisweethome.medialib.mapper.MovieExtendedMapper;
import org.keirobm.rpisweethome.medialib.mapper.SearchResultMapper;
import org.keirobm.rpisweethome.medialib.mapper.SeriesExtenderMapper;
import org.keirobm.rpisweethome.medialib.watchlist.input.SearchRefinement;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;
import org.keirobm.rpisweethome.medialib.watchlist.port.SearchTvProviderPort;
import org.keirobm.rpisweethome.medialib.watchlist.thirdparty.TvdbLangs;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchTvProviderAdapter implements SearchTvProviderPort {

    public static final long DEFAULT_LIMIT = 100L;

    private final TvdbApi tvdbApi;
    private final SearchResultMapper searchResultMapper;
    private final MovieExtendedMapper movieExtendedMapper;
    private final SeriesExtenderMapper seriesExtenderMapper;

    @Override
    public List<WatchlistItem> search(String query, Optional<SearchRefinement> searchRefinement) {
        final var limit = Optional.ofNullable(this.tvdbApi.getConfigProps().getLimit())
                .map(BigDecimal::valueOf).orElse(BigDecimal.valueOf(DEFAULT_LIMIT));
        final var year = searchRefinement.map(SearchRefinement::getYear)
                .map(BigDecimal::new).orElse(null);
        final var lang = searchRefinement.map(SearchRefinement::getLanguage)
                .map(TvdbLangs::getLangCode).orElse(null);

        final var searchResults = this.tvdbApi.getSearchApi().getSearchResults(query, null, null,
                year, null, null, null, lang, null, null,
                null, BigDecimal.ZERO, limit);

        return Optional.ofNullable(searchResults.getData()).orElse(List.of())
                .stream().map(searchResultMapper::fromSearchResult)
                .toList();
    }

    @Override
    public WatchlistItem getDetails(WatchlistItemType type, String externalId) {
        return switch (type) {
            case MOVIE -> getMovieDetails(externalId);
            case TV_SHOW -> getTvShowDetails(externalId);
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }

    private WatchlistItem getMovieDetails(String externalId) {
        final BigDecimal idToNumber = new BigDecimal(externalId);
        final var lang = TvdbLangs.SPA.getLangCode();

        final var response = this.tvdbApi.getMoviesApi().getMovieExtended(idToNumber,
                null, null);

        GetEpisodeTranslation200Response overviewTransResponse = null;
        String overview = "";
        try {
            overviewTransResponse = this.tvdbApi.getMoviesApi().getMovieTranslation(idToNumber, lang);
            overview = Optional.ofNullable(overviewTransResponse).map(GetEpisodeTranslation200Response::getData)
                    .map(Translation::getOverview).orElse("");
        } catch (final RestClientException restEx) {
            log.error("Error getting movie translation", restEx);
        }

        return this.movieExtendedMapper.fromExtendedRecord(response.getData())
                .toBuilder()
                .overview(overview)
                .build();
    }

    private WatchlistItem getTvShowDetails(String externalId) {
        final BigDecimal idToNumber = new BigDecimal(externalId);
        final var lang = TvdbLangs.SPA.getLangCode();

        final var response = this.tvdbApi.getSeriesApi().getSeriesExtended(idToNumber,
                null, null);

        GetEpisodeTranslation200Response overviewTransResponse = null;
        String overview = "";
        try {
            overviewTransResponse = this.tvdbApi.getSeriesApi().getSeriesTranslation(idToNumber, lang);
            overview = Optional.ofNullable(overviewTransResponse).map(GetEpisodeTranslation200Response::getData)
                    .map(Translation::getOverview).orElse("");
        } catch (final RestClientException restEx) {
            log.error("Error getting series overview translation", restEx);
        }

        final var tvShow = this.seriesExtenderMapper.fromExtendedRecord(response.getData())
                .toBuilder()
                .overview(overview)
                .build();

        // TODO Fetch seasons


        // TODO Fetch episodes

        return tvShow;
    }

}
