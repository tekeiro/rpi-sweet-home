package org.keirobm.rpisweethome.adapter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.entities.MovieEntity;
import org.keirobm.rpisweethome.entities.TvShowEntity;
import org.keirobm.rpisweethome.mapper.EpisodeDataMapper;
import org.keirobm.rpisweethome.mapper.MovieDataMapper;
import org.keirobm.rpisweethome.mapper.SeasonDataMapper;
import org.keirobm.rpisweethome.mapper.TvShowDataMapper;
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
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class WatchlistPersistenceAdapter implements WatchlistPersistencePort {

    private final MovieRepository movieRepository;
    private final TvShowRepository tvShowRepository;
    private final SeasonRepository seasonRepository;
    private final EpisodeRepository episodeRepository;

    private final MovieDataMapper movieDataMapper;
    private final TvShowDataMapper tvShowDataMapper;
    private final SeasonDataMapper seasonDataMapper;
    private final EpisodeDataMapper episodeDataMapper;

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
        final var entity = this.movieDataMapper.toNewEntity(movie);
        this.movieRepository.save(entity);
        return movie.toBuilder().id(entity.getId()).build();
    }

    private TvShow addTvShowToWatchlist(TvShow item) {
        final var tvShowEntity = this.tvShowDataMapper.toNewEntity(item);
        final var tvShowEntitySaved = this.tvShowRepository.save(tvShowEntity);
        // Save all seasons
        item.getSeasons().forEach(season -> {
            final var seasonEntity = this.seasonDataMapper.toNewEntity(tvShowEntitySaved, season);
            final var seasonEntitySaved = this.seasonRepository.save(seasonEntity);

            // Save all episodes
            season.getEpisodes().forEach(episode -> {
               final var episodeEntity = this.episodeDataMapper.toNewEntity(seasonEntitySaved, episode);
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
            final var movie = this.fromEntity(movieEntity);
            items.add(movie);
        });

        // Read tv shows
        this.tvShowRepository.findAll().forEach(tvShowEntity -> {
           final TvShow tvShow = this.fromEntity(tvShowEntity);
           items.add(tvShow);
        });

        return items;
    }

    private Movie fromEntity(MovieEntity entity) {
        return this.movieDataMapper.fromEntity(entity);
    }

    private TvShow fromEntity(TvShowEntity tvShowEntity) {
        final TvShow tvShow = this.tvShowDataMapper.fromEntity(tvShowEntity);
        final List<Season> seasons = new ArrayList<>();
        this.seasonRepository.findByTvShowId(tvShow.getId()).forEach(seasonEntity -> {
            final var season = this.seasonDataMapper.fromEntity(seasonEntity)
                    .toBuilder().tvShow(tvShow).build();

            final List<Episode> episodes = new ArrayList<>();
            this.episodeRepository.findBySeasonId(seasonEntity.getId()).forEach(episodeEntity -> {
                final var episode = this.episodeDataMapper.fromEntity(episodeEntity)
                        .toBuilder().season(season).build();
                episodes.add(episode);
                season.setEpisodes(episodes);
            });

            seasons.add(season);
            tvShow.setSeasons(seasons);
        });
        return tvShow;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<WatchlistItem> getItemById(Long id) {
        final var movie = this.movieRepository.findById(id).map(this::fromEntity)
                .map(o -> (WatchlistItem) o);
        final var tvShow = this.tvShowRepository.findById(id).map(this::fromEntity)
                .map(o -> (WatchlistItem) o);
        return movie.isPresent() ? movie : tvShow;
    }

    @Transactional
    @Override
    public WatchlistItem persist(WatchlistItem item) {
        if (item instanceof Movie movie) {
            final var entity = this.movieDataMapper.toNewEntity(movie);
            return this.movieDataMapper.fromEntity(
                    this.movieRepository.save(entity)
            );
        }
        else if (item instanceof TvShow tvShow) {
            final var entity = this.tvShowDataMapper.toNewEntity(tvShow);
            tvShow.getSeasons().forEach(season -> {
                final var seasonEntity = this.seasonDataMapper.toNewEntity(entity, season);
                this.seasonRepository.save(seasonEntity);
                season.getEpisodes().forEach(episode -> {
                    final var episodeEntity = this.episodeDataMapper.toNewEntity(seasonEntity, episode);
                    this.episodeRepository.save(episodeEntity);
                });
            });
            this.tvShowRepository.save(entity);
            return this.getItemById(item.getId()).orElse(null);
        }
        return null;
    }

}
