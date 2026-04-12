package org.keirobm.rpisweethome.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "episodes")
public class EpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private SeasonEntity season;

    @Column
    private Integer number;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "external_id", length = 50)
    private String externalId;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column
    private boolean watched;

    @Column(name = "to_download")
    private boolean toDownload;

}
