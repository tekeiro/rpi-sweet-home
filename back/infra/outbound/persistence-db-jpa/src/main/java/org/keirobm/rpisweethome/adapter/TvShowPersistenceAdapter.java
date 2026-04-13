package org.keirobm.rpisweethome.adapter;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.entities.TvShowEntity;
import org.keirobm.rpisweethome.mapper.EpisodeDataMapper;
import org.keirobm.rpisweethome.mapper.SeasonDataMapper;
import org.keirobm.rpisweethome.mapper.TvShowDataMapper;
import org.keirobm.rpisweethome.medialib.watchlist.model.Episode;
import org.keirobm.rpisweethome.medialib.watchlist.model.Season;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.keirobm.rpisweethome.medialib.watchlist.port.TvShowPersistencePort;
import org.keirobm.rpisweethome.repositories.EpisodeRepository;
import org.keirobm.rpisweethome.repositories.SeasonRepository;
import org.keirobm.rpisweethome.repositories.TvShowRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class TvShowPersistenceAdapter implements TvShowPersistencePort {

    private final TvShowRepository tvShowRepository;
    private final SeasonRepository seasonRepository;
    private final EpisodeRepository episodeRepository;

    private final TvShowDataMapper tvShowDataMapper;
    private final SeasonDataMapper seasonDataMapper;
    private final EpisodeDataMapper episodeDataMapper;


    public TvShow addTvShowToWatchlist(TvShow item) {
        final var tvShowEntity = this.tvShowDataMapper.toNewEntity(item.toBuilder().id(null).build());
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

    @Override
    public List<TvShow> getAll() {
        final var iterator = this.tvShowRepository.findAll(Sort.by("id").ascending());
        return StreamSupport.stream(iterator.spliterator(), false)
                .map(this::fromEntity)
                .toList();
    }

    @Override
    public Optional<TvShow> getById(Long id) {
        return this.tvShowRepository.findById(id)
                .map(this::fromEntity);
    }

    @Override
    public TvShow persist(TvShow tvShow) {
        final var entity = this.tvShowDataMapper.toNewEntity(tvShow);
        tvShow.getSeasons().forEach(season -> {
            final var seasonEntity = this.seasonDataMapper.toNewEntity(entity, season);
            this.seasonRepository.save(seasonEntity);
            season.getEpisodes().forEach(episode -> {
                final var episodeEntity = this.episodeDataMapper.toNewEntity(seasonEntity, episode);
                this.episodeRepository.save(episodeEntity);
            });
        });
        return this.getById(tvShow.getId()).orElse(null);
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

}
