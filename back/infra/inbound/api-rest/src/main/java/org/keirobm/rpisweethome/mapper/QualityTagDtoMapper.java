package org.keirobm.rpisweethome.mapper;

import org.keirobm.rpisweethome.medialib.search.model.QualityTag;
import org.keirobm.rpisweethome.model.QualityTagDTO;
import org.keirobm.rpisweethome.model.QualityTagFormDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QualityTagDtoMapper {

    QualityTagDTO toDto(QualityTag tag);

    QualityTag fromFormDto(QualityTagFormDTO dto);

}
