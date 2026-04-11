package org.keirobm.rpisweethome.medialib.controllers;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.usecases.NewSearchUseCase;
import org.keirobm.rpisweethome.medialib.watchlist.input.SearchRefinement;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.thirdparty.TvdbLangs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final NewSearchUseCase newSearchUseCase;

    @GetMapping
    public List<WatchlistItem> search(
            @RequestParam(required = true) String query,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "language", required = false) TvdbLangs language
    ) {
        Optional<SearchRefinement> searchRefinement = Optional.empty();
        if (year != null || language != null) {
            searchRefinement = Optional.of(SearchRefinement.builder()
                    .year(year)
                    .language(language)
                    .build());
        }
        return this.newSearchUseCase.executeSearch(query, searchRefinement);
    }

}
