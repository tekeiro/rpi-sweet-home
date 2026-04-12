package org.keirobm.rpisweethome.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "seasons")
public class SeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tv_show_id")
    private TvShowEntity tvShow;

    @Column
    private Integer number;

    @Column(name = "external_id", length = 50)
    private String externalId;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column
    private boolean watched;

    @Column(name = "to_download")
    private boolean toDownload;

}
