package org.keirobm.rpisweethome.controller;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.api.QualityTagsApi;
import org.keirobm.rpisweethome.mapper.QualityTagDtoMapper;
import org.keirobm.rpisweethome.model.QualityTagDTO;
import org.keirobm.rpisweethome.model.QualityTagFormDTO;
import org.keirobm.rpisweethome.search.usecases.QualityTagCrudUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QualityTagsController implements QualityTagsApi {

    private final QualityTagCrudUseCase qualityTagCrudUseCase;
    private final QualityTagDtoMapper qualityTagDtoMapper;

    @Override
    public ResponseEntity<Void> v1QualityTagByItemDelete(BigDecimal id) {
        this.qualityTagCrudUseCase.delete(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<QualityTagDTO> v1QualityTagByItemGet(BigDecimal id) {
        final var tagOpt = this.qualityTagCrudUseCase.getById(id.longValue());
        return tagOpt
                .map(tag -> ResponseEntity.ok(this.qualityTagDtoMapper.toDto(tag)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<QualityTagDTO> v1QualityTagByItemPut(BigDecimal id, QualityTagFormDTO qualityTagFormDTO) {
        final var tag = this.qualityTagDtoMapper.fromFormDto(qualityTagFormDTO);
        final var tagSaved = this.qualityTagCrudUseCase.update(id.longValue(), tag);
        return ResponseEntity.ok(this.qualityTagDtoMapper.toDto(tagSaved));
    }

    @Override
    public ResponseEntity<QualityTagDTO> v1QualityTagCreatePost(QualityTagFormDTO qualityTagFormDTO) {
        final var tag = this.qualityTagDtoMapper.fromFormDto(qualityTagFormDTO);
        final var tagCreated = this.qualityTagCrudUseCase.create(tag);
        return ResponseEntity.created(null).body(this.qualityTagDtoMapper.toDto(tagCreated));
    }

    @Override
    public ResponseEntity<List<QualityTagDTO>> v1QualityTagListGet() {
        final var tags = this.qualityTagCrudUseCase.getAll();
        return ResponseEntity.ok(tags.stream().map(this.qualityTagDtoMapper::toDto).toList());
    }

}
