package org.keirobm.rpisweethome.adapter;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.mapper.MovieDataMapper;
import org.keirobm.rpisweethome.medialib.watchlist.model.Movie;
import org.keirobm.rpisweethome.medialib.watchlist.port.MoviePersistencePort;
import org.keirobm.rpisweethome.repositories.MovieRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class MoviePersistenceAdapter implements MoviePersistencePort {

    private final MovieRepository movieRepository;
    private final MovieDataMapper movieDataMapper;

    public Movie addMovieToWatchlist(Movie movie) {
        final var entity = this.movieDataMapper.toNewEntity(movie.toBuilder().id(null).build());
        final var entitySaved = this.movieRepository.save(entity);
        return movie.toBuilder().id(entitySaved.getId()).build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Movie> getAll() {
        final var moviesIterable = this.movieRepository.findAll(Sort.by("id").ascending());
        return StreamSupport.stream(moviesIterable.spliterator(), false)
                .map(this.movieDataMapper::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Movie> getById(Long id) {
        return this.movieRepository.findById(id).map(this.movieDataMapper::fromEntity);
    }

    @Transactional
    @Override
    public Movie persist(Movie movie) {
        final var entity = this.movieDataMapper.toNewEntity(movie);
        return this.movieDataMapper.fromEntity(this.movieRepository.save(entity));
    }

}
