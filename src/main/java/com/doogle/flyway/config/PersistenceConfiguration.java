package com.doogle.flyway.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.RepairResult;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig mySQLHikariConfig() {
        return new HikariConfig();
    }

    @Bean(name = "dataSource")
    @FlywayDataSource
    @Primary
    public DataSource dataSource() {
        return new HikariDataSource(mySQLHikariConfig());
    }

    @Bean
    @ConfigurationProperties(prefix = "h2.datasource.hikari")
    public HikariConfig h2HikariConfig() {
        return new HikariConfig();
    }

    @Bean(name = "h2DataSource")
    @FlywayDataSource
    public DataSource h2DataSource() {
        return new HikariDataSource(h2HikariConfig());
    }

    @PostConstruct
    public void migrate(){
        var source = h2DataSource();
        var flyway = Flyway.configure()
                .locations("classpath:db/migrationh2")
                .dataSource(source).load();
        RepairResult r = flyway.repair();
        System.out.println(r );
        flyway.migrate();
        source = dataSource();
        flyway = Flyway.configure()
                .dataSource(source).load();
        r = flyway.repair();
        System.out.println(r );
        flyway.migrate();
    }

}