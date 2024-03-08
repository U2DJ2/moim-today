package bookie_today.util;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class ImplementTest {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }
}
