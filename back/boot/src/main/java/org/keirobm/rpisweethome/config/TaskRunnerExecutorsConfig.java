package org.keirobm.rpisweethome.config;

import org.keirobm.rpisweethome.runner.ExecutorProps;
import org.keirobm.rpisweethome.runner.TaskRunner;
import org.keirobm.rpisweethome.runner.TaskRunnerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskRunnerExecutorsConfig {

    @Bean
    @ConfigurationProperties("rpi-sweet-home.executors.io-executor")
    public ExecutorProps ioExecutorProps() {
        return new ExecutorProps();
    }

    @Bean
    @Qualifier("ioExecutor")
    public TaskRunner ioExecutor(ExecutorProps ioExecutorProps) {
        return TaskRunnerFactory.createFrom(ioExecutorProps);
    }

}
