package org.keirobm.rpisweethome.providers.mejortorrent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.keirobm.rpisweethome.config.MejorTorrentConfigProps;
import org.keirobm.rpisweethome.medialib.search.model.LinkSearchRequest;
import org.keirobm.rpisweethome.medialib.search.model.LinkSearchResult;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.shared.ScrapperProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MejorTorrentScrapperProvider implements ScrapperProvider {

    private final MejorTorrentConfigProps props;
    private final MejorTorrentMovieScrapper movieScrapper;

    public static final String PROVIDER_KEY = "MEJORTORRENTS";

    @Override
    public String providerKey() {
        return PROVIDER_KEY;
    }

    @Override
    public List<LinkSearchResult> search(LinkSearchRequest request) {
        final var url = buildUrl(request);
        log.info("Searching on MejorTorrents: {}", url);

        return switch (request.getItem().getType()) {
            case MOVIE -> movieScrapper.search(url, request);
            case TV_SHOW -> List.of();
        };
    }

    private String buildUrl(LinkSearchRequest request) {
        final var queryTerm = Optional.ofNullable(request.getItem())
                .map(WatchlistItem::getTitle).orElse("")
                .replace(" ", "+");
        return String.format(
                String.format("%s%s", props.getBaseUrl(), props.getQueryPath()),
                queryTerm);
    }

}
