package vrp.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import vrp.MonitoringApplication;
import vrp.service.CustomRestAppender;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitoringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql( value = { "classpath:/sql/delete_all_data.sql", "classpath:/sql/insert_test_data.sql"}
    , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CustomRestAppenderTest {

    private static final String SQL_QUERY = "SELECT * FROM monitoring.logs";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void correctSendLogAndSaveToDBTest() throws JSONException {
        final Logger logger = LogManager.getLogger(CustomRestAppenderTest.class.getName());
        logger.removeAppender("appender_for_test");

        logger.error("Test message");

        final List<String> textLogs = jdbcTemplate.query(SQL_QUERY, (rs,Long) -> rs.getString("text_log"));
        assertEquals(1, textLogs.size());
        assertEquals("Test message",new JSONObject(textLogs.get(0)).getString("textLog"));
    }

    @Test
    public void notSavedLogWithLessLevelToDBTest(){
        final Logger logger = LogManager.getLogger(CustomRestAppenderTest.class.getName());

        logger.debug("Test message");

        final List<String> textLogs = jdbcTemplate.query(SQL_QUERY, (rs,Long) -> rs.getString("text_log"));
        assertEquals(0, textLogs.size());
    }

    @Test(expected = RuntimeException.class)
    public void incorrectDataForSaveLogToDB(){
        final Logger logger = LogManager.getLogger(CustomRestAppenderTest.class.getName());
        final CustomRestAppender appender = new CustomRestAppender();
        appender.setName("appender_for_test");
        appender.setProjectName("unsaved_project_in_db");
        appender.setModuleName("unsaved_module_in_db");
        appender.setCredBasicAuth("incorrect_user:user");
        appender.setRestURL("http://localhost:8687/module_event/save");
        appender.setLayout(new PatternLayout("{ \"message\" : \"%m\" }"));
        logger.addAppender(appender);

        logger.info("Test message");
    }
}
