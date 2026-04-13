package org.keirobm.rpisweethome.medialib.watchlist.port;

import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;

import java.util.List;
import java.util.Optional;

public interface TvShowPersistencePort {
    List<TvShow> getAll();
    Optional<TvShow> getById(Long id);
    TvShow persist(TvShow tvShow);
}
