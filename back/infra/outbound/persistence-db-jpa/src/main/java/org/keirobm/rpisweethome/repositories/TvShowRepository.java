package org.keirobm.rpisweethome.repositories;

import org.keirobm.rpisweethome.common.BaseRepository;
import org.keirobm.rpisweethome.entities.TvShowEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowRepository extends BaseRepository<TvShowEntity, Long> {
}
