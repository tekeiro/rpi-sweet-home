package org.keirobm.rpisweethome.adapters;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.search.model.QualityTag;
import org.keirobm.rpisweethome.medialib.search.port.QualityTagPersistencePort;
import org.keirobm.rpisweethome.medialib.search.port.QualityTagPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QualityTagAdapter implements QualityTagPort {

    private final QualityTagPersistencePort qualityTagPersistencePort;

    @Override
    public List<QualityTag> applyQualityTags(List<String> currentTags) {
        return this.qualityTagPersistencePort.getAll().stream()
                .filter(qt -> currentTags.stream().anyMatch(ct ->
                        ct.equalsIgnoreCase(qt.getTag()) ||
                                (qt.getAliases() != null && qt.getAliases().stream().anyMatch(ct::equalsIgnoreCase))
                ))
                .distinct()
                .toList();
    }
}
