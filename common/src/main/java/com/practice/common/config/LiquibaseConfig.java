package com.practice.common.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class LiquibaseConfig {

    private static final String CHANGELOG_PATH = "db/changelog/db.changelog-master.yaml";

    @Bean
    public LiquibaseMigrationsApplied liquibaseMigrationsApplied(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            try (Liquibase liquibase = new Liquibase(CHANGELOG_PATH, new ClassLoaderResourceAccessor(), database)) {
                liquibase.update();
            }
        } catch (SQLException | LiquibaseException e) {
            throw new IllegalStateException("Failed to apply Liquibase migrations for common schema", e);
        }
        return new LiquibaseMigrationsApplied();
    }

    public static final class LiquibaseMigrationsApplied {}
}
