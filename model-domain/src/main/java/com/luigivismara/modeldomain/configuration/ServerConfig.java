package com.luigivismara.modeldomain.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.SERVER_CONNECTION_TIMEOUT;

@Slf4j
@Configuration
public class ServerConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customTomcat() {
        return factory -> factory
                .addConnectorCustomizers(connector -> connector
                        .setProperty("connectionTimeout", SERVER_CONNECTION_TIMEOUT)
                );
    }

    @Bean(name = {"async"})
    public Executor asyncExecutor() {
        final var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async Thread - ");
        executor.initialize();
        log.info("Async thread pool initialized {}{}", executor.getThreadNamePrefix(), executor.getThreadGroup());
        return executor;
    }

    @Bean(name = {"kafka"})
    public Executor kafkaExecutor() {
        final var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("Kafka Thread - ");
        executor.initialize();
        log.info("Kafka thread pool initialized {}{}", executor.getThreadNamePrefix(), executor.getThreadGroup());
        return executor;
    }
}
