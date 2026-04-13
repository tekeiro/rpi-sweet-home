package org.keirobm.rpisweethome.medialib.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.medialib.watchlist.input.UpdateSeasonOrEpisode;
import org.keirobm.rpisweethome.medialib.watchlist.model.Episode;
import org.keirobm.rpisweethome.medialib.watchlist.model.Season;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;
import org.keirobm.rpisweethome.medialib.watchlist.port.WatchlistPersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateSeasonOrEpisodeUseCase {

    private final WatchlistPersistencePort watchlistPersistencePort;

    public Season updateSeason(Long id, Integer seasonNumber, UpdateSeasonOrEpisode request) {
        return this.watchlistPersistencePort.getItemById(WatchlistItemType.TV_SHOW, id)
                .filter(item -> item instanceof TvShow)
                .map(item -> (TvShow) item)
                .map(tvShow -> {
                    tvShow.getSeasonByNumber(seasonNumber).ifPresent(season -> {
                        if (request.getMarkToDownload() != null) {
                            season.setToDownload(request.getMarkToDownload());
                        }
                        if (request.getMarkToWatch() != null) {
                            season.setWatched(request.getMarkToWatch());
                        }
                    });
                    return tvShow;
                })
                .map(this.watchlistPersistencePort::persist)
                .map(tvShow -> (TvShow) tvShow)
                .map(tvShow -> tvShow.getSeasonByNumber(seasonNumber).orElse(null))
                .orElse(null);
    }

    public Episode updateEpisode(Long id, Integer seasonNumber, Integer episodeNumber, UpdateSeasonOrEpisode request) {
        return this.watchlistPersistencePort.getItemById(WatchlistItemType.TV_SHOW, id)
                .filter(item -> item instanceof TvShow)
                .map(item -> (TvShow) item)
                .map(tvShow -> {
                    tvShow.getSeasonByNumber(seasonNumber)
                            .flatMap(season -> season.getEpisodeByNumber(episodeNumber))
                            .ifPresent(episode -> {
                                if (request.getMarkToDownload() != null) {
                                    episode.setToDownload(request.getMarkToDownload());
                                }
                                if (request.getMarkToWatch() != null) {
                                    episode.setWatched(request.getMarkToWatch());
                                }
                    });
                    return tvShow;
                })
                .map(this.watchlistPersistencePort::persist)
                .map(tvShow -> (TvShow) tvShow)
                .map(tvShow -> tvShow.getSeasonByNumber(seasonNumber).orElse(null))
                .map(season -> season.getEpisodeByNumber(episodeNumber).orElse(null))
                .orElse(null);
    }

}
