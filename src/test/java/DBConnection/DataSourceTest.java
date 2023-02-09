package DBConnection;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml", "file:web/WEB-INF/dispatcher-context.xml", "file:web/WEB-INF/security-context.xml"})
public class DataSourceTest {

    private static final Logger logger = LogManager.getLogger();

    private DataSource dataSource;

    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    void setUp() {
        assertNotNull(dataSource);
        assertNotNull(sqlSessionFactory);
    }

    @Test
    void testMyBatis() {
        try (SqlSession session = sqlSessionFactory.openSession();
             Connection conn = session.getConnection()) {
            logger.info(session);
            logger.info(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }
}
