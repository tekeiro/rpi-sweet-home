package org.keirobm.rpisweethome.mapper;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.keirobm.rpisweethome.model.WatchlistItemDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WatchlistItemMapper {

    private final SeasonMapper seasonMapper;

    public WatchlistItemDTO toDto(WatchlistItem item) {
        final var dto = new WatchlistItemDTO();
        if (item == null) {
            return dto;
        }

        dto.setType(WatchlistItemDTO.TypeEnum.valueOf(item.getType().name()));
        dto.setId(item.getId());
        dto.setTitle(item.getTitle());
        dto.setYear(item.getYear());
        dto.setOverview(item.getOverview());
        dto.setImageUrl(item.getImageUrl());
        dto.setGenres(item.getGenres());
        dto.setExternalId(item.getExternalId());
        dto.setWatched(item.isWatched());
        dto.setToDownload(item.isToDownload());
//        if (item instanceof TvShow tvShow) {
//            dto.setOnAir(WatchlistItemDTO.OnAirEnum.valueOf(tvShow.getOnAir().name()));
//            dto.setSeasons(
//                    tvShow.getSeasons().stream()
//                            .map(this.seasonMapper::toDto)
//                            .toList()
//            );
//        }
        return dto;
    }

}
