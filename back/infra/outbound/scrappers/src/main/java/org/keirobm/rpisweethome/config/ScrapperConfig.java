package org.keirobm.rpisweethome.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperConfig {

    @Bean
    @ConfigurationProperties("rpi-sweet-home.scrappers.mejor-torrent")
    public MejorTorrentConfigProps mejorTorrentConfigProps() {
        return new MejorTorrentConfigProps();
    }

}
