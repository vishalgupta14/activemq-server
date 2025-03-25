package com.activemq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@Profile("POSTGRES")
@EnableJpaRepositories(basePackages = {"com.platform.audit"})
public class MySQLConfig {

    private final DatabaseTypeConfig databaseTypeConfig;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    public MySQLConfig(DatabaseTypeConfig databaseTypeConfig) {
        this.databaseTypeConfig = databaseTypeConfig;
    }

    @Bean
    public DataSource dataSource() {
        if (databaseTypeConfig.getMap().containsValue("POSTGRES")) {
            return DataSourceBuilder.create().url(url)
                    .driverClassName(driverClassName)
                    .username(username)
                    .password(password)
                    .build();
        } else {
            return null;
        }
    }
}
