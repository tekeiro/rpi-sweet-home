package org.keirobm.rpisweethome.medialib.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keirobm.rpisweethome.common.events.EventBus;
import org.keirobm.rpisweethome.medialib.watchlist.events.AddedItemToWatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;
import org.keirobm.rpisweethome.medialib.watchlist.port.SearchTvProviderPort;
import org.keirobm.rpisweethome.medialib.watchlist.port.WatchlistPersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddToWatchlistUseCase {

    private final SearchTvProviderPort searchTvProviderPort;
    private final WatchlistPersistencePort watchlistPersistencePort;

    public WatchlistItem addToWatchlist(WatchlistItemType type, String externalId) {
        final var watchlistItem = this.searchTvProviderPort.getDetails(type, externalId);
        final var item = this.watchlistPersistencePort.addToWatchlist(watchlistItem);
        EventBus.publish(new AddedItemToWatchlistItem(item));
        return item;
    }

}
