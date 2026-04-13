package org.keirobm.rpisweethome.model;

import lombok.Builder;
import lombok.Data;
import org.keirobm.rpisweethome.medialib.search.model.ILinkSearchCandidate;
import org.keirobm.rpisweethome.medialib.search.model.QualityTag;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class LinkResultIntermediate implements ILinkSearchCandidate {
    private String linkTitle;
    private String cleanTitle;
    private List<String> tags;
    private List<QualityTag> qualityTags;
    private String linkUrl;
    private MejorTorrentLinkType type;
    private Double score;
    private Double titleCoincidence;
}
