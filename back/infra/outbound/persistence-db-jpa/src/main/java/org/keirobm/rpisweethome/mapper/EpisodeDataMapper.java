package org.keirobm.rpisweethome.mapper;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.entities.EpisodeEntity;
import org.keirobm.rpisweethome.entities.SeasonEntity;
import org.keirobm.rpisweethome.medialib.watchlist.model.Episode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EpisodeDataMapper {

    private final SeasonDataMapper seasonDataMapper;

    public EpisodeEntity toNewEntity(SeasonEntity seasonEntity, Episode episode) {
        final EpisodeEntity entity = new EpisodeEntity();
        entity.setId(episode.getId());
        entity.setSeason(seasonEntity);
        entity.setTitle(episode.getTitle());
        entity.setNumber(episode.getNumber());
        entity.setExternalId(episode.getExternalId());
        entity.setOverview(episode.getOverview());
        entity.setImageUrl(episode.getImageUrl());
        entity.setWatched(episode.isWatched());
        entity.setToDownload(episode.isToDownload());
        return entity;
    }

    public Episode fromEntity(EpisodeEntity entity) {
        return Episode.builder()
                .id(entity.getId())
                .season(this.seasonDataMapper.fromEntity(entity.getSeason()))
                .number(entity.getNumber())
                .title(entity.getTitle())
                .externalId(entity.getExternalId())
                .overview(entity.getOverview())
                .imageUrl(entity.getImageUrl())
                .watched(entity.isWatched())
                .toDownload(entity.isToDownload())
                .build();
    }

}
