package org.keirobm.rpisweethome;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.keirobm.rpisweethome")
public class AppJpaConfig {
}
