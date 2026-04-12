CREATE SEQUENCE IF NOT EXISTS movies_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE movies
(
    id          BIGINT NOT NULL,
    title       VARCHAR(255),
    year        INTEGER,
    overview    TEXT,
    image_url   VARCHAR(255),
    genres      VARCHAR(255),
    external_id VARCHAR(50),
    watched     BOOLEAN,
    to_download BOOLEAN,
    CONSTRAINT pk_movies PRIMARY KEY (id)
);