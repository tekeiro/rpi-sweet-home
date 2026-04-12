package org.keirobm.rpisweethome.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@MappedSuperclass
public abstract class WatchlistItemEntity {

    @Column
    protected String title;

    @Column
    protected Integer year;

    @Column(columnDefinition = "TEXT")
    protected String overview;

    @Column(name = "image_url", length = 255)
    protected String imageUrl;

    @Column
    protected String genres;

    @Column(name = "external_id", length = 50)
    protected String externalId;

    @Column
    protected boolean watched;

    @Column(name = "to_download")
    protected boolean toDownload;

    public void fromGenresList(List<String> genres) {
        if (genres == null || genres.isEmpty()) {
            this.genres = "";
        }
        this.genres = String.join("|", genres);
    }

    public List<String> toGenresList() {
        return this.genres != null ? List.of(this.genres.split("\\|")) : List.of();
    }

}
