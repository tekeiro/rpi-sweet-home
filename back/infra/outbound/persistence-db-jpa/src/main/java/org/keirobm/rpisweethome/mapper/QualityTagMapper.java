package org.keirobm.rpisweethome.mapper;

import org.keirobm.rpisweethome.entities.qualityTags.QualityTagEntity;
import org.keirobm.rpisweethome.medialib.search.model.QualityTag;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface QualityTagMapper {

    QualityTagEntity toEntity(QualityTag qualityTag);

    QualityTag fromEntity(QualityTagEntity qualityTagEntity);

}
