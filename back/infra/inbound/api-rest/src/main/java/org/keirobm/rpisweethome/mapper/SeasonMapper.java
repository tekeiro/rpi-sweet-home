package org.keirobm.rpisweethome.mapper;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.watchlist.model.Season;
import org.keirobm.rpisweethome.model.SeasonDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeasonMapper {

    private final EpisodeMapper episodeMapper;

    public SeasonDTO toDto(Season season) {
        final SeasonDTO dto = new SeasonDTO();
        dto.setNumber(season.getNumber());
        dto.setExternalId(season.getExternalId());
        dto.setImageUrl(season.getImageUrl());
        dto.setWatched(season.isWatched());
        dto.setToDownload(season.isToDownload());
        dto.setEpisodes(
                season.getEpisodes().stream()
                        .map(this.episodeMapper::toDto)
                        .toList()
        );
        return dto;
    }

}
