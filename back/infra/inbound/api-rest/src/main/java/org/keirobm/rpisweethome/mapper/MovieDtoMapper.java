package org.keirobm.rpisweethome.mapper;

import org.keirobm.rpisweethome.medialib.watchlist.model.Movie;
import org.keirobm.rpisweethome.model.MovieDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieDtoMapper {

    MovieDTO toDto(Movie movie);

}
