package org.keirobm.rpisweethome.shared;

import org.keirobm.rpisweethome.medialib.search.model.LinkSearchRequest;
import org.keirobm.rpisweethome.medialib.search.model.LinkSearchResult;

import java.util.List;

public interface ScrapperProvider {
    String providerKey();
    List<LinkSearchResult> search(LinkSearchRequest request);
}
