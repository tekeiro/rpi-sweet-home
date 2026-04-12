package org.keirobm.rpisweethome.mapper;

import org.keirobm.rpisweethome.medialib.watchlist.model.Episode;
import org.keirobm.rpisweethome.model.EpisodeDTO;
import org.springframework.stereotype.Component;

@Component
public class EpisodeMapper {

    public EpisodeDTO toDto(Episode episode) {
        final EpisodeDTO dto = new EpisodeDTO();
        dto.setNumber(episode.getNumber());
        dto.setTitle(episode.getTitle());
        dto.setExternalId(episode.getExternalId());
        dto.setOverview(episode.getOverview());
        dto.setImageUrl(episode.getImageUrl());
        dto.setWatched(episode.isWatched());
        dto.setToDownload(episode.isToDownload());
        return dto;
    }

}
