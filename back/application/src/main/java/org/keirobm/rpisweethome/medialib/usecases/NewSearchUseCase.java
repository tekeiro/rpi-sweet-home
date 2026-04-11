package org.keirobm.rpisweethome.medialib.usecases;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.medialib.watchlist.input.SearchRefinement;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.port.SearchTvProviderPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewSearchUseCase {

    private final SearchTvProviderPort searchTvProviderPort;

    public List<WatchlistItem> executeSearch(@Nonnull String query, Optional<SearchRefinement> searchRefinement) {
        return this.searchTvProviderPort.search(query, searchRefinement);
    }

}
