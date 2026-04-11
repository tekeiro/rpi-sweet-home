package org.keirobm.rpisweethome.medialib.watchlist.input;

import lombok.Builder;
import lombok.Data;
import org.keirobm.rpisweethome.medialib.watchlist.thirdparty.TvdbLangs;

@Data
@Builder(toBuilder = true)
public class SearchRefinement {
    private Integer year;
    /** Three character for language. (example: spa for spanish) **/
    private TvdbLangs language;
}
