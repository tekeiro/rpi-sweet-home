package org.keirobm.rpisweethome.medialib.watchlist.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.keirobm.rpisweethome.common.events.BaseEvent;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddedItemToWatchlistItem extends BaseEvent {
    private final WatchlistItem item;

    public String toString() {
        return String.format("AddedItemToWatchlistItemEvent{ '%s(%d)' }", item.getTitle(), item.getYear());
    }
}
