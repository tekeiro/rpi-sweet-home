package org.keirobm.rpisweethome.entities;

import jakarta.persistence.*;
import lombok.*;
import org.keirobm.rpisweethome.medialib.watchlist.model.OnAirStatus;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "tv_shows")
public class TvShowEntity extends WatchlistItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "on_air")
    @Enumerated(EnumType.STRING)
    private OnAirStatus onAir;

}
