package org.keirobm.rpisweethome.mapper;

import org.keirobm.rpisweethome.entities.TvShowEntity;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.springframework.stereotype.Component;

@Component
public class TvShowMapper {

    public TvShowEntity toNewEntity(TvShow tvShow) {
        final TvShowEntity entity = new TvShowEntity();
        entity.setId(tvShow.getId());
        entity.setTitle(tvShow.getTitle());
        entity.setYear(tvShow.getYear());
        entity.setOverview(tvShow.getOverview());
        entity.setImageUrl(tvShow.getImageUrl());
        entity.fromGenresList(tvShow.getGenres());
        entity.setExternalId(tvShow.getExternalId());
        entity.setWatched(tvShow.isWatched());
        entity.setToDownload(tvShow.isToDownload());
        entity.setOnAir(tvShow.getOnAir());
        return entity;
    }

    public TvShow fromEntity(TvShowEntity entity) {
        return TvShow.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .year(entity.getYear())
                .overview(entity.getOverview())
                .imageUrl(entity.getImageUrl())
                .genres(entity.toGenresList())
                .externalId(entity.getExternalId())
                .watched(entity.isWatched())
                .toDownload(entity.isToDownload())
                .onAir(entity.getOnAir())
                .build();
    }

}
