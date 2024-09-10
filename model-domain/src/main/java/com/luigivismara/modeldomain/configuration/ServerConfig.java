package com.luigivismara.modeldomain.configuration;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.luigivismara.modeldomain.configuration.ConstantsVariables.SERVER_CONNECTION_TIMEOUT;

@Configuration
public class ServerConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customTomcat() {
        return factory -> factory
                .addConnectorCustomizers(connector -> connector
                        .setProperty("connectionTimeout", SERVER_CONNECTION_TIMEOUT)
                );
    }
}
