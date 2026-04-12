package org.keirobm.rpisweethome.medialib.controllers;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.dto.AddToWatchlistRequest;
import org.keirobm.rpisweethome.medialib.usecases.AddToWatchlistUseCase;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final AddToWatchlistUseCase addToWatchlistUseCase;


    @PostMapping
    public WatchlistItem addItemToWatchlist(@RequestBody AddToWatchlistRequest request) {
        return this.addToWatchlistUseCase.addToWatchlist(request.getType(), request.getExternalId());
    }

}
