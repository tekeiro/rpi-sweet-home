package org.keirobm.rpisweethome.controller;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.api.MoviesApi;
import org.keirobm.rpisweethome.mapper.MovieDtoMapper;
import org.keirobm.rpisweethome.medialib.usecases.MoviesCrudUseCase;
import org.keirobm.rpisweethome.medialib.watchlist.input.UpdateWatchlistItem;
import org.keirobm.rpisweethome.model.MovieDTO;
import org.keirobm.rpisweethome.model.PutWatchlistItemDTO;
import org.keirobm.rpisweethome.model.V1MoviesCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MoviesController implements MoviesApi {

    private final MoviesCrudUseCase moviesCrudUseCase;
    private final MovieDtoMapper movieDtoMapper;

    @Override
    public ResponseEntity<MovieDTO> v1MoviesByIdGet(BigDecimal id) {
        return this.moviesCrudUseCase.getMovieById(id.longValue())
                .map(movieDtoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<MovieDTO> v1MoviesByIdPut(BigDecimal id, PutWatchlistItemDTO putWatchlistItemDTO) {
        final UpdateWatchlistItem request = UpdateWatchlistItem.builder()
                .markToWatch(putWatchlistItemDTO.getMarkAsWatched())
                .markToDownload(putWatchlistItemDTO.getMarkToDownload())
                .build();
        final var movie = this.moviesCrudUseCase.updateMovie(id.longValue(), request);
        return movie != null
                ? ResponseEntity.ok(movieDtoMapper.toDto(movie))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<MovieDTO> v1MoviesCreate(V1MoviesCreateRequest v1MoviesCreateRequest) {
        final var movie = this.moviesCrudUseCase.addMovie(v1MoviesCreateRequest.getExternalId());
        return movie != null
                ? ResponseEntity.ok(movieDtoMapper.toDto(movie))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<MovieDTO>> v1MoviesList() {
        return ResponseEntity.ok(
                this.moviesCrudUseCase.getAllMovies().stream()
                    .map(movieDtoMapper::toDto)
                    .toList()
        );
    }
}
