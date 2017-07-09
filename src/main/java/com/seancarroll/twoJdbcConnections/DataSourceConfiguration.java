package com.seancarroll.twoJdbcConnections;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public PlatformTransactionManager firstDataSourceTransactionManager() {
        return new DataSourceTransactionManager(firstDataSource());
    }

    @Bean(destroyMethod = "shutdown")
    @Primary
    public DataSource firstDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .build();
    }

    @Bean
    public JdbcTemplate firstJdbcTemplate() {
        return new JdbcTemplate(firstDataSource());
    }

    @Bean
    Flyway firstFlyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("classpath:/db/migrations/first/");
        flyway.setDataSource(firstDataSource());

        flyway.migrate();

        return flyway;
    }

    @Bean
    public PlatformTransactionManager secondDataSourceTransactionManager() {
        return new DataSourceTransactionManager(secondDataSource());
    }

    @Bean(destroyMethod = "shutdown")
    public DataSource secondDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .build();
    }

    @Bean
    public JdbcTemplate secondJdbcTemplate() {
        return new JdbcTemplate(secondDataSource());
    }

    @Bean
    Flyway secondFlyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations("classpath:/db/migrations/second/");
        flyway.setDataSource(secondDataSource());

        flyway.migrate();

        return flyway;
    }
}
