package org.keirobm.rpisweethome.medialib.search.port;

import org.keirobm.rpisweethome.medialib.search.model.QualityTag;

import java.util.List;
import java.util.Optional;

public interface QualityTagPersistencePort {
    QualityTag persist(QualityTag qualityTag);
    List<QualityTag> getAll();
    Optional<QualityTag> getById(Long id);
    void deleteById(Long id);
}
