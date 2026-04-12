package org.keirobm.rpisweethome.controller;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.api.SearchApi;
import org.keirobm.rpisweethome.mapper.WatchlistItemMapper;
import org.keirobm.rpisweethome.medialib.usecases.NewSearchUseCase;
import org.keirobm.rpisweethome.medialib.watchlist.input.SearchRefinement;
import org.keirobm.rpisweethome.medialib.watchlist.thirdparty.TvdbLangs;
import org.keirobm.rpisweethome.model.WatchlistItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SearchController implements SearchApi {

    private final NewSearchUseCase newSearchUseCase;

    private final WatchlistItemMapper watchlistItemMapper;

    @Override
    public ResponseEntity<List<WatchlistItemDTO>> v1MediaLibSearch(String query, Integer year, String language) {
        Optional<SearchRefinement> searchRefinement = Optional.empty();
        if (year != null || language != null) {
            searchRefinement = Optional.of(SearchRefinement.builder()
                    .year(year)
                    .language(TvdbLangs.valueOf(language))
                    .build());
        }

        final var searchResults = this.newSearchUseCase.executeSearch(query, searchRefinement);
        return ResponseEntity.ok(
                searchResults.stream().map(this.watchlistItemMapper::toDto).toList()
        );
    }

}
