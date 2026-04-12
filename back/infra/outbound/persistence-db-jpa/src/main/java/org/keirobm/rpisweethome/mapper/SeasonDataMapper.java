package org.keirobm.rpisweethome.mapper;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.entities.SeasonEntity;
import org.keirobm.rpisweethome.entities.TvShowEntity;
import org.keirobm.rpisweethome.medialib.watchlist.model.Season;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeasonDataMapper {

    private final TvShowDataMapper tvShowDataMapper;

    public SeasonEntity toNewEntity(TvShowEntity tvShowEntity, Season season) {
        final SeasonEntity entity = new SeasonEntity();
        entity.setId(season.getId());
        entity.setTvShow(tvShowEntity);
        entity.setNumber(season.getNumber());
        entity.setExternalId(season.getExternalId());
        entity.setImageUrl(season.getImageUrl());
        entity.setWatched(season.isWatched());
        entity.setToDownload(season.isToDownload());
        return entity;
    }

    public Season fromEntity(SeasonEntity entity) {
        return Season.builder()
                .id(entity.getId())
                .tvShow(this.tvShowDataMapper.fromEntity(entity.getTvShow()))
                .number(entity.getNumber())
                .externalId(entity.getExternalId())
                .imageUrl(entity.getImageUrl())
                .watched(entity.isWatched())
                .toDownload(entity.isToDownload())
                .build();
    }

}
