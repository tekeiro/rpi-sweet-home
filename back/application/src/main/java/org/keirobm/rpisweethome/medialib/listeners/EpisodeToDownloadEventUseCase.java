package org.keirobm.rpisweethome.medialib.listeners;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.common.events.EventBus;
import org.keirobm.rpisweethome.common.events.EventListener;
import org.keirobm.rpisweethome.medialib.search.model.LinkSearchRequest;
import org.keirobm.rpisweethome.medialib.search.port.LinkSearchPort;
import org.keirobm.rpisweethome.medialib.watchlist.events.TvShowEpisodeToDownloadEvent;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.keirobm.rpisweethome.runner.TaskRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EpisodeToDownloadEventUseCase implements EventListener<TvShowEpisodeToDownloadEvent> {

    private final TaskRunner ioExecutor;
    private final LinkSearchPort linkSearchPort;

    @PostConstruct
    void init() {
        EventBus.register(TvShowEpisodeToDownloadEvent.class, this);
    }

    @Override
    public void onEvent(TvShowEpisodeToDownloadEvent event) {
        ioExecutor.submit("on-episode-to-download-"+event.toString(), () ->
                this.downloadEpisode(event));
    }

    private TvShow downloadEpisode(TvShowEpisodeToDownloadEvent event) {
        final var tvShow = event.getTvShow();
        final var searchResults = this.linkSearchPort.search(LinkSearchRequest.builder()
                .item(tvShow)
                .season(event.getSeason().getNumber())
                .episode(event.getEpisode().getNumber())
                .build());
        return tvShow;
    }

}
