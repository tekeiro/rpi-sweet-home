package org.keirobm.rpisweethome.controller;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.api.WatchlistApi;
import org.keirobm.rpisweethome.mapper.WatchlistItemMapper;
import org.keirobm.rpisweethome.medialib.usecases.AddToWatchlistUseCase;
import org.keirobm.rpisweethome.medialib.usecases.WatchlistItemUseCase;
import org.keirobm.rpisweethome.medialib.watchlist.input.UpdateWatchlistItem;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItemType;
import org.keirobm.rpisweethome.model.PutWatchlistItemDTO;
import org.keirobm.rpisweethome.model.V1MediaLibAddItemToWatchlistRequest;
import org.keirobm.rpisweethome.model.WatchlistItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WatchlistController implements WatchlistApi {

    private final AddToWatchlistUseCase addWatchlistItemUseCase;
    private final WatchlistItemUseCase watchlistItemUseCase;

    private final WatchlistItemMapper watchlistItemMapper;

    @Override
    public ResponseEntity<WatchlistItemDTO> v1MediaLibAddItemToWatchlist(
            V1MediaLibAddItemToWatchlistRequest v1MediaLibAddItemToWatchlistRequest
    ) {
        final var type = WatchlistItemType.valueOf(v1MediaLibAddItemToWatchlistRequest.getType().name());
        final var externalId = v1MediaLibAddItemToWatchlistRequest.getExternalId();

        final var itemAdded = this.addWatchlistItemUseCase.addToWatchlist(type, externalId);
        return ResponseEntity.ok(this.watchlistItemMapper.toDto(itemAdded));
    }

    @Override
    public ResponseEntity<List<WatchlistItemDTO>> v1MediaLibGetWatchlistItems() {
        final var items = this.watchlistItemUseCase.getItems();
        return ResponseEntity.ok(
                items.stream().map(this.watchlistItemMapper::toDto).toList()
        );
    }
}
