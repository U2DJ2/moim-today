package moim_today.util;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;
    private final List<String> tableNames = new ArrayList<>();

    public DatabaseCleaner(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    public void cleanUp() {
        for (String tableName : tableNames) {
            jdbcTemplate.execute("TRUNCATE table " + tableName);
        }
    }

    @PostConstruct
    public void findAllTables() {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        for (EntityType<?> entity : entities) {
            Class<?> javaType = entity.getJavaType();
            Table table = javaType.getAnnotation(Table.class);

            if (table != null) {
                String tableName = table.name();
                tableNames.add(tableName);
            }
        }
    }
}