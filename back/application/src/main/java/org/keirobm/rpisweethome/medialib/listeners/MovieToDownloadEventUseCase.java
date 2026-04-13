package org.keirobm.rpisweethome.medialib.listeners;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.common.events.EventBus;
import org.keirobm.rpisweethome.common.events.EventListener;
import org.keirobm.rpisweethome.medialib.watchlist.events.MovieToDownloadEvent;
import org.keirobm.rpisweethome.medialib.watchlist.model.Movie;
import org.keirobm.rpisweethome.runner.TaskRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieToDownloadEventUseCase implements EventListener<MovieToDownloadEvent> {

    private final TaskRunner ioExecutor;

    @PostConstruct
    public void init() {
        EventBus.register(MovieToDownloadEvent.class, this);
    }

    @Override
    public void onEvent(MovieToDownloadEvent event) {
        ioExecutor.submit("on-movie-to-download", () -> this.downloadMovie(event));
    }

    private Movie downloadMovie(MovieToDownloadEvent evt) {
        final var movie = evt.getMovie();

        return movie;
    }

}
