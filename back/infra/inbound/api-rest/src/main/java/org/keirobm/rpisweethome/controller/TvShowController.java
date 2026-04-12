package org.keirobm.rpisweethome.controller;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.api.TvShowsApi;
import org.keirobm.rpisweethome.mapper.EpisodeMapper;
import org.keirobm.rpisweethome.mapper.SeasonMapper;
import org.keirobm.rpisweethome.medialib.usecases.UpdateSeasonOrEpisodeUseCase;
import org.keirobm.rpisweethome.medialib.watchlist.input.UpdateSeasonOrEpisode;
import org.keirobm.rpisweethome.model.EpisodeDTO;
import org.keirobm.rpisweethome.model.PutSeasonOrEpisodeDTO;
import org.keirobm.rpisweethome.model.SeasonDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class TvShowController implements TvShowsApi {

    private final UpdateSeasonOrEpisodeUseCase updateSeasonOrEpisodeUseCase;

    private final SeasonMapper seasonMapper;
    private final EpisodeMapper episodeMapper;

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
