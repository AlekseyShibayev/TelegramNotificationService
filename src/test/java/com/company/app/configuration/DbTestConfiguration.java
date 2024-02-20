package com.company.app.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


@TestConfiguration
public class DbTestConfiguration {

    @Bean
    @Primary
    public HikariDataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(TestContainers.getPostgresContainer().getJdbcUrl());
        ds.setPassword(TestContainers.getPostgresContainer().getPassword());
        ds.setUsername(TestContainers.getPostgresContainer().getUsername());
        ds.setDriverClassName(Driver.class.getCanonicalName());
        ds.setMinimumIdle(1);
        ds.setMaximumPoolSize(5);
        ds.setMaxLifetime(5000);
        return ds;
    }

}
