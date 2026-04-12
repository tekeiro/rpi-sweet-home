package org.keirobm.rpisweethome.medialib.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.port.WatchlistPersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetWatchlistItemsUseCase {

    private final WatchlistPersistencePort watchlistPersistencePort;

    public List<WatchlistItem> getItems() {
        return this.watchlistPersistencePort.getWatchlistItems();
    }

}
