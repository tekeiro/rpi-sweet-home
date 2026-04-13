package org.keirobm.rpisweethome.adapter;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.mapper.QualityTagMapper;
import org.keirobm.rpisweethome.medialib.search.model.QualityTag;
import org.keirobm.rpisweethome.medialib.search.port.QualityTagPersistencePort;
import org.keirobm.rpisweethome.repositories.QualityTagRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class QualityTagPersistenceAdapter implements QualityTagPersistencePort {

    private final QualityTagRepository qualityTagRepository;
    private final QualityTagMapper qualityTagMapper;

    @Override
    public QualityTag persist(QualityTag qualityTag) {
        final var entity = this.qualityTagMapper.toEntity(qualityTag);
        final var entitySaved = this.qualityTagRepository.save(entity);
        return this.qualityTagMapper.fromEntity(entitySaved);
    }

    @Override
    public List<QualityTag> getAll() {
        final List<QualityTag> tags = new ArrayList<>();
        this.qualityTagRepository.findAll(Sort.by("id").ascending()).forEach(entity -> {
            final var tag = this.qualityTagMapper.fromEntity(entity);
            tags.add(tag);
        });
        return tags;
    }

    @Override
    public Optional<QualityTag> getById(Long id) {
        return this.qualityTagRepository.findById(id)
                .map(this.qualityTagMapper::fromEntity);
    }

    @Override
    public void deleteById(Long id) {
        this.qualityTagRepository.deleteById(id);
    }
}
