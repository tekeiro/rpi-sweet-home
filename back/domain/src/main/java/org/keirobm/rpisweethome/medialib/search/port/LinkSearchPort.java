package org.keirobm.rpisweethome.medialib.search.port;

import org.keirobm.rpisweethome.medialib.search.model.LinkSearchRequest;
import org.keirobm.rpisweethome.medialib.search.model.LinkSearchResult;

import java.util.List;

public interface LinkSearchPort {
    List<LinkSearchResult> search(LinkSearchRequest request);
}
