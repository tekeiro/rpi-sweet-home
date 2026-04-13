package org.keirobm.rpisweethome.medialib.search.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class QualityTag {
    private Long id;
    private String tag;
    private List<String> aliases;
    private Double score;
    private Boolean excludeImmediately;
}
