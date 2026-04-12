package org.keirobm.rpisweethome.medialib.watchlist.thirdparty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TvdbLangs {
    SPA("spa"),
    ENG("eng");

    private final String langCode;

    public static final TvdbLangs DEFAULT_LANG = SPA;

}
