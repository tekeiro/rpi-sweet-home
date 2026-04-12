package org.keirobm.rpisweethome.mapper;

import org.keirobm.rpisweethome.entities.MovieEntity;
import org.keirobm.rpisweethome.medialib.watchlist.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieEntity toNewEntity(Movie movie) {
        final MovieEntity entity = new MovieEntity();
        entity.setId(movie.getId());
        entity.setTitle(movie.getTitle());
        entity.setYear(movie.getYear());
        entity.setOverview(movie.getOverview());
        entity.setImageUrl(movie.getImageUrl());
        entity.fromGenresList(movie.getGenres());
        entity.setExternalId(movie.getExternalId());
        entity.setWatched(movie.isWatched());
        entity.setToDownload(movie.isToDownload());
        return entity;
    }

    public Movie fromEntity(MovieEntity entity) {
        return Movie.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .year(entity.getYear())
                .overview(entity.getOverview())
                .imageUrl(entity.getImageUrl())
                .genres(entity.toGenresList())
                .externalId(entity.getExternalId())
                .watched(entity.isWatched())
                .toDownload(entity.isToDownload())
                .build();
    }

}
