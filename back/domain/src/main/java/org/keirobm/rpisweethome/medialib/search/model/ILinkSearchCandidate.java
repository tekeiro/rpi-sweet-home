package org.keirobm.rpisweethome.medialib.search.model;

import java.util.List;

public interface ILinkSearchCandidate {
    /** Score calculated after apply quality tags. **/
    Double getScore();
    /** Percentage of title coincidence. Number between 0.0 - 100.0 **/
    Double getTitleCoincidence();
    List<QualityTag> getQualityTags();
}
