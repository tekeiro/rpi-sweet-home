package org.keirobm.rpisweethome.medialib.search.port;

import org.keirobm.rpisweethome.medialib.search.model.QualityTag;

import java.util.List;

public interface QualityTagPort {
    List<QualityTag> applyQualityTags(List<String> currentTags);
}
