package org.keirobm.rpisweethome.repositories;

import org.keirobm.rpisweethome.common.BaseRepository;
import org.keirobm.rpisweethome.entities.SeasonEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends BaseRepository<SeasonEntity, Long> {
    void deleteByTvShowId(Long tvShowId);
}
