package org.keirobm.rpisweethome.mapper;

import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.keirobm.rpisweethome.model.TvShowDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TvShowDtoMapper {

    TvShowDTO toDto(TvShow tvShow);

}
