CREATE SEQUENCE IF NOT EXISTS episodes_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS seasons_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS tv_shows_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE episodes
(
    id          BIGINT NOT NULL,
    season_id   BIGINT,
    number      INTEGER,
    title       TEXT,
    external_id VARCHAR(50),
    overview    TEXT,
    image_url   VARCHAR(255),
    watched     BOOLEAN,
    to_download BOOLEAN,
    CONSTRAINT pk_episodes PRIMARY KEY (id)
);

CREATE TABLE seasons
(
    id          BIGINT NOT NULL,
    tv_show_id  BIGINT,
    number      INTEGER,
    external_id VARCHAR(50),
    image_url   TEXT,
    watched     BOOLEAN,
    to_download BOOLEAN,
    CONSTRAINT pk_seasons PRIMARY KEY (id)
);

CREATE TABLE tv_shows
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
    on_air      VARCHAR(255),
    CONSTRAINT pk_tv_shows PRIMARY KEY (id)
);

ALTER TABLE episodes
    ADD CONSTRAINT FK_EPISODES_ON_SEASON FOREIGN KEY (season_id) REFERENCES seasons (id);

ALTER TABLE seasons
    ADD CONSTRAINT FK_SEASONS_ON_TV_SHOW FOREIGN KEY (tv_show_id) REFERENCES tv_shows (id);