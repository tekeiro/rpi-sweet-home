package org.keirobm.rpisweethome.controller;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.api.TvShowsApi;
import org.keirobm.rpisweethome.mapper.EpisodeMapper;
import org.keirobm.rpisweethome.mapper.SeasonMapper;
import org.keirobm.rpisweethome.mapper.TvShowDtoMapper;
import org.keirobm.rpisweethome.medialib.usecases.TvShowCrudUseCase;
import org.keirobm.rpisweethome.medialib.usecases.UpdateSeasonOrEpisodeUseCase;
import org.keirobm.rpisweethome.medialib.watchlist.input.UpdateSeasonOrEpisode;
import org.keirobm.rpisweethome.medialib.watchlist.input.UpdateWatchlistItem;
import org.keirobm.rpisweethome.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TvShowController implements TvShowsApi {

    private final UpdateSeasonOrEpisodeUseCase updateSeasonOrEpisodeUseCase;
    private final TvShowCrudUseCase tvShowCrudUseCase;

    private final TvShowDtoMapper tvShowDtoMapper;
    private final SeasonMapper seasonMapper;
    private final EpisodeMapper episodeMapper;

    @Override
    public ResponseEntity<TvShowDTO> v1TvShowByIdGet(BigDecimal id) {
        return this.tvShowCrudUseCase.getTvShowById(id.longValue())
                .map(tvShow -> ResponseEntity.ok(this.tvShowDtoMapper.toDto(tvShow)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<TvShowDTO> v1TvShowCreate(V1MoviesCreateRequest v1MoviesCreateRequest) {
        final var tvShow = this.tvShowCrudUseCase.addTvShow(v1MoviesCreateRequest.getExternalId());
        return tvShow != null ? ResponseEntity.ok(this.tvShowDtoMapper.toDto(tvShow))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<TvShowDTO>> v1TvShowList() {
        final var tvShows = this.tvShowCrudUseCase.getAllTvShows();
        return ResponseEntity.ok(tvShows.stream().map(this.tvShowDtoMapper::toDto).toList());
    }

    @Override
    public ResponseEntity<TvShowDTO> v1TvShowsByIdPut(BigDecimal id, PutWatchlistItemDTO putWatchlistItemDTO) {
        final UpdateWatchlistItem request = UpdateWatchlistItem.builder()
                .markToWatch(putWatchlistItemDTO.getMarkAsWatched())
                .markToDownload(putWatchlistItemDTO.getMarkToDownload())
                .build();
        final var tvShow = this.tvShowCrudUseCase.updateTvShow(id.longValue(), request);
        return tvShow != null ? ResponseEntity.ok(this.tvShowDtoMapper.toDto(tvShow))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<SeasonDTO> v1MediaLibUpdateSeason(BigDecimal id, BigDecimal season, PutSeasonOrEpisodeDTO putSeasonOrEpisodeDTO) {
        final UpdateSeasonOrEpisode dto = UpdateSeasonOrEpisode.builder()
                .markToDownload(putSeasonOrEpisodeDTO.getMarkToDownload())
                .markToWatch(putSeasonOrEpisodeDTO.getMarkAsWatched())
                .build();
        final var seasonModel = this.updateSeasonOrEpisodeUseCase.updateSeason(id.longValue(), season.intValue(), dto);
        return seasonModel != null
                ? ResponseEntity.ok(this.seasonMapper.toDto(seasonModel))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<EpisodeDTO> v1MediaLibUpdateEpisode(BigDecimal id, BigDecimal season, BigDecimal episode, PutSeasonOrEpisodeDTO putSeasonOrEpisodeDTO) {
        final UpdateSeasonOrEpisode dto = UpdateSeasonOrEpisode.builder()
                .markToDownload(putSeasonOrEpisodeDTO.getMarkToDownload())
                .markToWatch(putSeasonOrEpisodeDTO.getMarkAsWatched())
                .build();
        final var episodeModel = this.updateSeasonOrEpisodeUseCase.updateEpisode(id.longValue(),
                season.intValue(), episode.intValue(), dto);
        return episodeModel != null
                ? ResponseEntity.ok(this.episodeMapper.toDto(episodeModel))
                : ResponseEntity.notFound().build();
    }

}
