package org.keirobm.rpisweethome.medialib.services;

import com.tvdb.v4.model.GetSeasonExtended200Response;
import com.tvdb.v4.model.GetSeriesArtworks200Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.medialib.TvdbApi;
import org.keirobm.rpisweethome.medialib.watchlist.model.Episode;
import org.keirobm.rpisweethome.medialib.watchlist.model.Season;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.keirobm.rpisweethome.runner.TaskRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class FetchSeasonsAndEpisodeService {

    private final TaskRunner ioExecutor;
    private final TvdbApi tvdbApi;


    public List<Season> fetchSeasons(TvShow tvShow, GetSeriesArtworks200Response record) {
        final List<Season> seasons = new ArrayList<>();
        final Map<Integer, Season> seasonMap = new HashMap<>();

        record.getData().getSeasons().forEach(seasonData -> {
            if (! seasonMap.containsKey(seasonData.getNumber().intValue())) {
                final var externalId = seasonData.getId();
                final var number = seasonData.getNumber().intValue();
                final var image = seasonData.getImage();
                final var season = Season.builder()
                        .tvShow(tvShow)
                        .number(number)
                        .externalId(String.valueOf(externalId))
                        .imageUrl(image)
                        .watched(false)
                        .toDownload(false)
                        .build();
                seasonMap.put(number, season);
                seasons.add(season);
            }
        });
        return seasons;
    }

    public void fillEpisodes(TvShow tvShow, List<Season> seasons) {
        final Map<Integer, CompletableFuture<GetSeasonExtended200Response>> seasonTasks = new HashMap<>();
        final Map<Integer, Season> seasonMap = new HashMap<>();

        seasons.forEach(season -> {
           final var getSeasonTask = this.ioExecutor.submit("fetch-season-"+season.getNumber(), () ->
                   this.tvdbApi.getSeasonsApi().getSeasonExtended(new BigDecimal(season.getExternalId())));
           seasonTasks.put(season.getNumber(), getSeasonTask);
           seasonMap.put(season.getNumber(), season);
        });

        seasonTasks.forEach((seasonNumber, getSeasonInfoTask) -> {
            final var season = seasonMap.get(seasonNumber);
            final var seasonInfo = getSeasonInfoTask.join();

            final List<Episode> episodes = new ArrayList<>();
            seasonInfo.getData().getEpisodes().forEach(episodeRecord -> {
                final var number = episodeRecord.getNumber();
                final var title = episodeRecord.getName();
                final var overview = Optional.ofNullable(episodeRecord.getOverview()).orElse("");
                final var externalId = String.valueOf(episodeRecord.getId());
                final var imageUrl = Optional.ofNullable(episodeRecord.getImage()).orElse("");

                final var episode = Episode.builder()
                        .season(season)
                        .number(number)
                        .title(title)
                        .overview(overview)
                        .externalId(externalId)
                        .imageUrl(imageUrl)
                        .watched(false)
                        .toDownload(false)
                        .build();
                episodes.add(episode);
            });
            season.setEpisodes(episodes);
        });
    }

}
