CREATE SEQUENCE IF NOT EXISTS quality_tags_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE quality_tag_aliases
(
    quality_tag_id BIGINT NOT NULL,
    aliases        VARCHAR(255)
);

CREATE TABLE quality_tags
(
    id                  BIGINT NOT NULL,
    tag                 VARCHAR(255),
    score               DOUBLE PRECISION,
    exclude_immediately BOOLEAN,
    CONSTRAINT pk_quality_tags PRIMARY KEY (id)
);

ALTER TABLE quality_tag_aliases
    ADD CONSTRAINT fk_quality_tag_aliases_on_quality_tag_entity FOREIGN KEY (quality_tag_id) REFERENCES quality_tags (id);