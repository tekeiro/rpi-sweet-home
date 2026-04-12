package org.keirobm.rpisweethome.adapter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.entities.MovieEntity;
import org.keirobm.rpisweethome.entities.TvShowEntity;
import org.keirobm.rpisweethome.mapper.EpisodeMapper;
import org.keirobm.rpisweethome.mapper.MovieMapper;
import org.keirobm.rpisweethome.mapper.SeasonMapper;
import org.keirobm.rpisweethome.mapper.TvShowMapper;
import org.keirobm.rpisweethome.medialib.watchlist.model.*;
import org.keirobm.rpisweethome.medialib.watchlist.port.WatchlistPersistencePort;
import org.keirobm.rpisweethome.repositories.EpisodeRepository;
import org.keirobm.rpisweethome.repositories.MovieRepository;
import org.keirobm.rpisweethome.repositories.SeasonRepository;
import org.keirobm.rpisweethome.repositories.TvShowRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class WatchlistPersistenceAdapter implements WatchlistPersistencePort {

    private final MovieRepository movieRepository;
    private final TvShowRepository tvShowRepository;
    private final SeasonRepository seasonRepository;
    private final EpisodeRepository episodeRepository;

    private final MovieMapper movieMapper;
    private final TvShowMapper tvShowMapper;
    private final SeasonMapper seasonMapper;
    private final EpisodeMapper episodeMapper;

    @Transactional
    @Override
    public WatchlistItem addToWatchlist(WatchlistItem item) {
        return switch (item.getType()) {
            case MOVIE -> this.addMovieToWatchlist((Movie) item);
            case TV_SHOW -> this.addTvShowToWatchlist((TvShow) item);
            case null, default -> throw new IllegalArgumentException("Unsupported type: " + item.getType());
        };
    }

    private Movie addMovieToWatchlist(Movie movie) {
        final var entity = this.movieMapper.toNewEntity(movie);
        this.movieRepository.save(entity);
        return movie.toBuilder().id(entity.getId()).build();
    }

    private TvShow addTvShowToWatchlist(TvShow item) {
        final var tvShowEntity = this.tvShowMapper.toNewEntity(item);
        final var tvShowEntitySaved = this.tvShowRepository.save(tvShowEntity);
        // Save all seasons
        item.getSeasons().forEach(season -> {
            final var seasonEntity = this.seasonMapper.toNewEntity(tvShowEntitySaved, season);
            final var seasonEntitySaved = this.seasonRepository.save(seasonEntity);

            // Save all episodes
            season.getEpisodes().forEach(episode -> {
               final var episodeEntity = this.episodeMapper.toNewEntity(seasonEntitySaved, episode);
               this.episodeRepository.save(episodeEntity);
            });
        });
        return item.toBuilder().id(tvShowEntitySaved.getId()).build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<WatchlistItem> getWatchlistItems() {
        final List<WatchlistItem> items = new ArrayList<>();

        // Read movies
        this.movieRepository.findAll().forEach(movieEntity -> {
            final var movie = this.movieMapper.fromEntity(movieEntity);
            items.add(movie);
        });

        // Read tv shows
        this.tvShowRepository.findAll().forEach(tvShowEntity -> {
           final TvShow tvShow = this.tvShowMapper.fromEntity(tvShowEntity);

           final List<Season> seasons = new ArrayList<>();
           this.seasonRepository.findByTvShowId(tvShow.getId()).forEach(seasonEntity -> {
               final var season = this.seasonMapper.fromEntity(seasonEntity)
                       .toBuilder().tvShow(tvShow).build();

               final List<Episode> episodes = new ArrayList<>();
              this.episodeRepository.findBySeasonId(seasonEntity.getId()).forEach(episodeEntity -> {
                  final var episode = this.episodeMapper.fromEntity(episodeEntity)
                          .toBuilder().season(season).build();
                  episodes.add(episode);
                  season.setEpisodes(episodes);
              });

              seasons.add(season);
              tvShow.setSeasons(seasons);
           });
           items.add(tvShow);
        });

        return items;
    }

}
