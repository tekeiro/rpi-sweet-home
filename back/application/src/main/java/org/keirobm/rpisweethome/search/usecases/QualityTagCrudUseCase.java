package org.keirobm.rpisweethome.search.usecases;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.common.events.EventBus;
import org.keirobm.rpisweethome.medialib.search.events.CreatedQualityTagEvent;
import org.keirobm.rpisweethome.medialib.search.events.DeletedQualityTagEvent;
import org.keirobm.rpisweethome.medialib.search.events.UpdatedQualityTagEvent;
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
        final var tagSaved = this.qualityTagPersistencePort.persist(qualityTag.toBuilder().id(null).build());
        EventBus.publish(new CreatedQualityTagEvent(tagSaved));
        return tagSaved;
    }

    public List<QualityTag> getAll() {
        return this.qualityTagPersistencePort.getAll();
    }

    public Optional<QualityTag> getById(Long id) {
        return this.qualityTagPersistencePort.getById(id);
    }

    public QualityTag update(Long id, QualityTag qualityTag) {
        final var tagSaved = this.qualityTagPersistencePort.persist(qualityTag.toBuilder().id(id).build());
        EventBus.publish(new UpdatedQualityTagEvent(tagSaved));
        return tagSaved;
    }

    public void delete(Long id) {
        this.qualityTagPersistencePort.getById(id).ifPresent(tag -> {
            this.qualityTagPersistencePort.deleteById(id);
            EventBus.publish(new DeletedQualityTagEvent(tag));
        });
    }

}
