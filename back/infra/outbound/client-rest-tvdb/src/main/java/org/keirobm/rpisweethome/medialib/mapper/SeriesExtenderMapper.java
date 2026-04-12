package org.keirobm.rpisweethome.medialib.mapper;

import com.tvdb.v4.model.GenreBaseRecord;
import com.tvdb.v4.model.SeriesExtendedRecord;
import org.keirobm.rpisweethome.medialib.watchlist.model.OnAirStatus;
import org.keirobm.rpisweethome.medialib.watchlist.model.TvShow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SeriesExtenderMapper {

    public TvShow fromExtendedRecord(SeriesExtendedRecord record) {
        final String title = record.getName();
        final var year = Integer.parseInt(record.getYear());
        final var genres = Optional.ofNullable(record.getGenres()).orElse(List.of()).stream()
                .map(GenreBaseRecord::getName).toList();
        final var imageUrl = record.getImage();
        final var externalId = String.valueOf(record.getId());

        OnAirStatus onAir = null;
        switch (record.getStatus().getName()) {
            case "Continuing":
                onAir = OnAirStatus.ON_AIR;
                break;
            case "Ended":
                onAir = OnAirStatus.FINISHED;
                break;
            case null, default:
                onAir = OnAirStatus.FINISHED;
                break;
        }


        return TvShow.builder()
                .title(title)
                .year(year)
                .overview(record.getOverview())
                .onAir(onAir)
                .imageUrl(imageUrl)
                .externalId(externalId)
                .genres(genres)
                .watched(false)
                .toDownload(false)
                .build();
    }
}
