package org.keirobm.rpisweethome.medialib.mapper;

import com.tvdb.v4.model.GenreBaseRecord;
import com.tvdb.v4.model.MovieExtendedRecord;
import org.keirobm.rpisweethome.medialib.watchlist.model.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MovieExtendedMapper {

    public Movie fromExtendedRecord(MovieExtendedRecord record) {
        final String title = record.getName();
        final var year = Integer.parseInt(record.getYear());
        final var genres = Optional.ofNullable(record.getGenres()).orElse(List.of()).stream()
                .map(GenreBaseRecord::getName).toList();
        final var imageUrl = record.getImage();
        final var externalId = String.valueOf(record.getId());

        return Movie.builder()
                .title(title)
                .year(year)
                .imageUrl(imageUrl)
                .externalId(externalId)
                .genres(genres)
                .build();
    }

}
