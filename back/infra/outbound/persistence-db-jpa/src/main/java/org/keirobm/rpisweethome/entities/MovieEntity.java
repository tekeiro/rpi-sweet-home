package org.keirobm.rpisweethome.entities;

import jakarta.persistence.*;
import lombok.*;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class MovieEntity extends WatchlistItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
