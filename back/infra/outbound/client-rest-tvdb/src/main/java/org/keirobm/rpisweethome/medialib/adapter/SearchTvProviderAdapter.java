package org.keirobm.rpisweethome.medialib.adapter;

import com.tvdb.v4.api.LoginApi;
import com.tvdb.v4.api.SearchApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.medialib.TvdbApi;
import org.keirobm.rpisweethome.medialib.mapper.SearchResultMapper;
import org.keirobm.rpisweethome.medialib.watchlist.input.SearchRefinement;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.port.SearchTvProviderPort;
import org.keirobm.rpisweethome.medialib.watchlist.thirdparty.TvdbLangs;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchTvProviderAdapter implements SearchTvProviderPort {

    public static final long DEFAULT_LIMIT = 100L;

    private final TvdbApi tvdbApi;
    private final SearchResultMapper searchResultMapper;

    @Override
    public List<WatchlistItem> search(String query, Optional<SearchRefinement> searchRefinement) {
        final var limit = Optional.ofNullable(this.tvdbApi.getConfigProps().getLimit())
                .map(BigDecimal::valueOf).orElse(BigDecimal.valueOf(DEFAULT_LIMIT));
        final var year = searchRefinement.map(SearchRefinement::getYear)
                .map(BigDecimal::new).orElse(null);
        final var lang = searchRefinement.map(SearchRefinement::getLanguage)
                .map(TvdbLangs::getLangCode).orElse(null);

        final var searchResults = this.tvdbApi.getSearchApi().getSearchResults(query, null, null,
                year, null, null, null, lang, null, null,
                null, BigDecimal.ZERO, limit);

        return Optional.ofNullable(searchResults.getData()).orElse(List.of())
                .stream().map(searchResultMapper::fromSearchResult)
                .toList();
    }

}
