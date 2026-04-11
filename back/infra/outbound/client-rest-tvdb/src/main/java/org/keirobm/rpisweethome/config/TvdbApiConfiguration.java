package org.keirobm.rpisweethome.config;

import com.tvdb.v4.ApiClient;
import com.tvdb.v4.api.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class TvdbApiConfiguration {

    public static final String TVDB_API_BASE_URL = "https://api.thetvdb.com/v4";

    @Bean
    @Qualifier("tvdbApi")
    public ApiClient tvdbApi(TvdbApiConfigProps props) {
        final var apiClient = new ApiClient();
        apiClient.setBasePath(Optional.ofNullable(props)
                .map(TvdbApiConfigProps::getBaseUrl)
                .orElse(TVDB_API_BASE_URL)
        );
        return apiClient;
    }

    @Bean
    public LoginApi loginApi(@Qualifier("tvdbApi") ApiClient apiClient) {
        return new LoginApi(apiClient);
    }

    @Bean
    public SearchApi searchApi(@Qualifier("tvdbApi") ApiClient apiClient) {
        return new SearchApi(apiClient);
    }

    @Bean
    public SeasonsApi seasonsApi(@Qualifier("tvdbApi") ApiClient apiClient) {
        return new SeasonsApi(apiClient);
    }

    @Bean
    public MoviesApi moviesApi(@Qualifier("tvdbApi") ApiClient apiClient) {
        return new MoviesApi(apiClient);
    }

    @Bean
    public EpisodesApi episodesApi(@Qualifier("tvdbApi") ApiClient apiClient) {
        return new EpisodesApi(apiClient);
    }

    @Bean
    public SeriesApi seriesApi(@Qualifier("tvdbApi") ApiClient apiClient) {
        return new SeriesApi(apiClient);
    }

}
