package org.keirobm.rpisweethome.providers.mejortorrent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.keirobm.rpisweethome.config.MejorTorrentConfigProps;
import org.keirobm.rpisweethome.medialib.search.model.LinkSearchRequest;
import org.keirobm.rpisweethome.medialib.search.model.LinkSearchResult;
import org.keirobm.rpisweethome.medialib.search.port.CalculateScorePort;
import org.keirobm.rpisweethome.medialib.search.port.QualityTagPort;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;
import org.keirobm.rpisweethome.model.LinkResultIntermediate;
import org.keirobm.rpisweethome.model.MejorTorrentLinkType;
import org.keirobm.rpisweethome.runner.TaskRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class MejorTorrentMovieScrapper {

    private final MejorTorrentConfigProps props;
    private final CalculateScorePort calculateScorePort;
    private final QualityTagPort qualityTagPort;
    private final TaskRunner ioExecutor;

    public List<LinkSearchResult> search(String queryUrl, LinkSearchRequest request) {

        try {
            final var rootPageDoc = Jsoup.connect(queryUrl).get();
            final var elements = rootPageDoc.select(".flex.w-full .bg-mejortorrent-yellow a");

            final List<LinkResultIntermediate> linkCandidates = new ArrayList<>();
            elements.forEach(element -> {
                final var elementType = MejorTorrentLinkType.fromKey(
                        element.parent().select("span").text());
                final var linkTitle = element.text();
                final var link = element.attr("href");

                // Filter proper content
                if (WatchlistItemType.MOVIE.equals(elementType.getContentType())) {
                    final var parsed = LinkTitleParser.parse(linkTitle);
                    final var qualityTags = this.qualityTagPort.applyQualityTags(parsed.tags());
                    final Double score = this.calculateScorePort.calculateScore(parsed.tags());
                    final Double titleCoincidence = this.calculateScorePort.calculateTitleCoincidence(parsed.title(),
                            request.getItem());
                    linkCandidates.add(LinkResultIntermediate.builder()
                            .linkTitle(linkTitle)
                            .cleanTitle(parsed.title())
                            .tags(parsed.tags())
                            .qualityTags(qualityTags)
                            .linkUrl(link)
                            .type(elementType)
                            .score(score)
                            .titleCoincidence(titleCoincidence)
                            .build());
                }
            });
            return linkCandidates.stream()
                    .map(candidate -> this.ioExecutor.submit("fetch-candidate-torrent-url-" + candidate.getLinkTitle(), () -> {
                        return this.fetchCandidate(request, candidate);
                    })).map(CompletableFuture::join).toList();
        } catch (IOException ioEx) {
            log.error("Error searching on MejorTorrents", ioEx);
            throw new RuntimeException(ioEx);
        }
    }

    // Go to details page and fetch torrent url
    private LinkSearchResult fetchCandidate(LinkSearchRequest request, LinkResultIntermediate link) {
        try {
            final var detailsPage = Jsoup.connect(link.getLinkUrl()).get();
            final var linkElement = detailsPage.select(".grid .flex.items-center a");
            final var torrentUrl = linkElement.attr("href");

            return LinkSearchResult.builder()
                    .fullTitle(link.getLinkTitle())
                    .title(link.getCleanTitle())
                    .providerKey(MejorTorrentScrapperProvider.PROVIDER_KEY)
                    .contentType(request.getItem().getType())
                    .tags(link.getQualityTags())
                    .item(request.getItem())
                    .torrentUrl(String.format("%s%s", props.getBaseUrl(), torrentUrl))
                    .score(link.getScore())
                    .titleCoincidence(link.getTitleCoincidence())
                    .build();
        } catch (IOException ioEx) {
            log.error("Error searching on MejorTorrents", ioEx);
            throw new RuntimeException(ioEx);
        }
    }

}
