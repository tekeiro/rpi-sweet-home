package org.keirobm.rpisweethome.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "tvdb.api")
public class TvdbApiConfigProps {
    private String apiKey;
    private Integer limit;
}
