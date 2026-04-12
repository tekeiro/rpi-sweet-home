package org.keirobm.rpisweethome.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<E, ID> extends CrudRepository<E, ID>, PagingAndSortingRepository<E, ID> {
}
