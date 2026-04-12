package org.keirobm.rpisweethome.repositories;

import org.keirobm.rpisweethome.common.BaseRepository;
import org.keirobm.rpisweethome.entities.EpisodeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends BaseRepository<EpisodeEntity, Long> {
    List<EpisodeEntity> findBySeasonId(Long seasonId);
}
