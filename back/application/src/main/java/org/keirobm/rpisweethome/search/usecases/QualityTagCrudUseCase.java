package org.keirobm.rpisweethome.search.usecases;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.search.model.QualityTag;
import org.keirobm.rpisweethome.medialib.search.port.QualityTagPersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QualityTagCrudUseCase {

    private final QualityTagPersistencePort qualityTagPersistencePort;

    public QualityTag create(QualityTag qualityTag) {
        return this.qualityTagPersistencePort.persist(qualityTag.toBuilder().id(null).build());
    }

    public List<QualityTag> getAll() {
        return this.qualityTagPersistencePort.getAll();
    }

    public Optional<QualityTag> getById(Long id) {
        return this.qualityTagPersistencePort.getById(id);
    }

    public QualityTag update(Long id, QualityTag qualityTag) {
        return this.qualityTagPersistencePort.persist(qualityTag.toBuilder().id(id).build());
    }

    public void delete(Long id) {
        this.qualityTagPersistencePort.deleteById(id);
    }

}
