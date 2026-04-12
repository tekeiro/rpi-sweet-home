package org.keirobm.rpisweethome.repositories;

import org.keirobm.rpisweethome.common.BaseRepository;
import org.keirobm.rpisweethome.entities.MovieEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends BaseRepository<MovieEntity, Long> {
}
