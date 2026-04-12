package org.keirobm.rpisweethome.medialib.services;

import com.tvdb.v4.model.GetEpisodeTranslation200Response;
import com.tvdb.v4.model.Translation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.medialib.TvdbApi;
import org.keirobm.rpisweethome.medialib.mapper.MovieExtendedMapper;
import org.keirobm.rpisweethome.medialib.mapper.SeriesExtenderMapper;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;
import org.keirobm.rpisweethome.medialib.watchlist.thirdparty.TvdbLangs;
import org.keirobm.rpisweethome.runner.TaskRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetItemDetailsService {

    private final TvdbApi tvdbApi;
    private final MovieExtendedMapper movieExtendedMapper;
    private final SeriesExtenderMapper seriesExtenderMapper;
    private final FetchSeasonsAndEpisodeService fetchSeasonsAndEpisodeService;
    private final TaskRunner ioExecutor;


    public WatchlistItem getDetails(WatchlistItemType type, String externalId) {
        return switch (type) {
            case MOVIE -> getMovieDetails(externalId);
            case TV_SHOW -> getTvShowDetails(externalId);
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }

    private Supplier<String> overviewSupplier(Supplier<GetEpisodeTranslation200Response> getOverviewSupplier) {
        return () -> {
            GetEpisodeTranslation200Response overviewTransResponse = null;
            String overview = "";
            try {
                overviewTransResponse = getOverviewSupplier.get();
                overview = Optional.ofNullable(overviewTransResponse).map(GetEpisodeTranslation200Response::getData)
                        .map(Translation::getOverview).orElse("");
            } catch (final RestClientException restEx) {
                log.error("Error getting movie translation", restEx);
            }
            return overview;
        };
    }

    private WatchlistItem getMovieDetails(String externalId) {
        final BigDecimal idToNumber = new BigDecimal(externalId);

        final var getMovieDetailsTask = this.ioExecutor.submit("get-movie-extended",
                () -> this.tvdbApi.getMoviesApi().getMovieExtended(idToNumber, null, null));
        final var getMovieOverviewTask = this.ioExecutor.submit("get-movie-overview",
                this.overviewSupplier(() -> this.tvdbApi.getMoviesApi().getMovieTranslation(idToNumber, TvdbLangs.DEFAULT_LANG.getLangCode())));

        final var overview = getMovieOverviewTask.join();
        final var response = getMovieDetailsTask.join();

        return this.movieExtendedMapper.fromExtendedRecord(response.getData())
                .toBuilder()
                .overview(overview)
                .build();
    }

    private WatchlistItem getTvShowDetails(String externalId) {
        final BigDecimal idToNumber = new BigDecimal(externalId);

        final var getSeriesDetailsTask = this.ioExecutor.submit("get-series-extended",
                () -> this.tvdbApi.getSeriesApi().getSeriesExtended(idToNumber, null, null));
        final var getSeriesOverviewTask = this.ioExecutor.submit("get-series-overview",
                this.overviewSupplier(() -> this.tvdbApi.getSeriesApi().getSeriesTranslation(idToNumber, TvdbLangs.DEFAULT_LANG.getLangCode())));

        final var seriesDetails = getSeriesDetailsTask.join();
        final var overview = getSeriesOverviewTask.join();

        final var tvShow = this.seriesExtenderMapper.fromExtendedRecord(seriesDetails.getData())
                .toBuilder()
                .overview(overview)
                .build();

        // Fetch seasons
        final var seasons = this.fetchSeasonsAndEpisodeService.fetchSeasons(tvShow, seriesDetails);
        tvShow.setSeasons(seasons);
        // Fetch episodes
        this.fetchSeasonsAndEpisodeService.fillEpisodes(tvShow, seasons);

        return tvShow;
    }

}
