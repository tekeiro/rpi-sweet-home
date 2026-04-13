package org.keirobm.rpisweethome.repositories;

import org.keirobm.rpisweethome.common.BaseRepository;
import org.keirobm.rpisweethome.entities.qualityTags.QualityTagEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface QualityTagRepository extends BaseRepository<QualityTagEntity, Long> {
}
