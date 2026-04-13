package org.keirobm.rpisweethome.entities.qualityTags;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "quality_tags")
public class QualityTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String tag;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "quality_tag_aliases", joinColumns = @JoinColumn(name = "quality_tag_id"))
    private List<String> aliases;

    @Column
    private Double score;

    @Column(name = "exclude_immediately")
    private Boolean excludeImmediately;


}
