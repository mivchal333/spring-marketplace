package pl.edu.pb.springmarketplace.integration;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@SpringBootTest(
        properties = "spring.datasource.type=com.zaxxer.hikari.HikariDataSource"
)
public class HikariIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void hikariConnectionPoolIsConfigured() {
        assertEquals("com.zaxxer.hikari.HikariDataSource", dataSource.getClass().getName());
    }
}