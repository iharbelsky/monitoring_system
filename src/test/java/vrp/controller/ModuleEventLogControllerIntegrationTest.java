package vrp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import vrp.MonitoringApplication;
import vrp.dto.ModuleEventLogDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitoringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = { "classpath:/sql/delete_all_data.sql","classpath:/sql/insert_test_data.sql"}
             , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ModuleEventLogControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_QUERY = new StringBuilder().append("SELECT projects.name_project, modules.name_module, logs.text_log ")
                                                               .append("FROM monitoring.logs ")
                                                               .append("INNER JOIN monitoring.modules ON modules.id=logs.id_module ")
                                                               .append("INNER JOIN monitoring.projects ON projects.id=modules.id_project ")
                                                               .append("WHERE (logs.text_log->>'text') = 'Build Error.404'")
                                                               .toString();

    @Test
    public void correctPostRequestAndSaveToDB() throws Exception {
        final var restTemplate = new TestRestTemplate();
        final var json = new ObjectMapper().writeValueAsString(new ModuleEventLogDTO( "internet-shop"
                                                                                    , "controller"
                                                                                    , "{\"text\":\"Build Error.404\"}"));
        final var requestBody = new HttpEntity<>(json, getHttpHeaders());
        final var responseEntity = restTemplate.exchange( getURI()
                                                        , HttpMethod.POST
                                                        , requestBody
                                                        , String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final var logs = jdbcTemplate.query( SQL_QUERY
                                           , (rs,Long) -> new ModuleEventLogDTO( rs.getString("name_project")
                                                                               , rs.getString("name_module")
                                                                               , rs.getString("text_log")) );
        Assert.assertEquals(1, logs.size());
    }

    @Test
    public void incorrectPostRequestAndSaveToDB() throws Exception {
        final var restTemplate = new TestRestTemplate();
        final var json = new ObjectMapper().writeValueAsString(new ModuleEventLogDTO( "incorrect_project"
                                                                                    , "controller"
                                                                                    , "{\"text\":\"Build Error.404\"}"));
        final var requestBody = new HttpEntity<>(json, getHttpHeaders());
        final var responseEntity = restTemplate.exchange( getURI()
                                                        , HttpMethod.POST
                                                        , requestBody
                                                        , String.class);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
        final var logs = jdbcTemplate.query( SQL_QUERY
                                           , (rs,Long) -> new ModuleEventLogDTO( rs.getString("name_project")
                                                                               , rs.getString("name_module")
                                                                               , rs.getString("text_log")) );
        Assert.assertEquals(0, logs.size());
    }

    protected String getURI(){
        return new StringBuilder().append("http://localhost:")
                                  .append(port)
                                  .append("/module_event/save")
                                  .toString();
    }

    protected HttpHeaders getHttpHeaders() {
        final var headers = new HttpHeaders();
        final var plainCredentials = "user:user";
        final var base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
