package org.keirobm.rpisweethome.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.medialib.search.model.LinkSearchRequest;
import org.keirobm.rpisweethome.medialib.search.model.LinkSearchResult;
import org.keirobm.rpisweethome.medialib.search.port.LinkSearchPort;
import org.keirobm.rpisweethome.shared.ScrapperProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LinkSearchAdapter implements LinkSearchPort {

    private final List<ScrapperProvider> scrapperProviders;

    @Override
    public List<LinkSearchResult> search(LinkSearchRequest request) {
        // TODO Re-do
        final List<LinkSearchResult> results = new ArrayList<>();
        this.scrapperProviders.forEach(provider -> results.addAll(provider.search(request)));
        return results;
    }

}
