package info.eati;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class PgTestContainersLifecycleManager implements QuarkusTestResourceLifecycleManager  {
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("PgTestContainersLifecycleManager")
            .withReuse(false);


    @Override
    public Map<String, String> start() {
        POSTGRES.start();

        Map<String, String> config = new HashMap<>();
        config.put("quarkus.datasource.jdbc.url", POSTGRES.getJdbcUrl());
        config.put("quarkus.datasource.username", POSTGRES.getUsername());
        config.put("quarkus.datasource.password", POSTGRES.getPassword());
        config.put("quarkus.datasource.jdbc.driver", "org.postgresql.Driver");
        config.put("quarkus.datasource.db-kind", "postgresql");

        return config;

    }

    @Override
    public void stop() {
        if (POSTGRES.isRunning()) {
            POSTGRES.stop();
        }
    }
}
