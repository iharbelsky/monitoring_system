package vrp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import vrp.MonitoringApplication;
import vrp.dto.LogDTO;
import vrp.repository.LogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitoringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "classpath:/sql/delete_all_logs.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LogControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private LogRepository logRepository;

    @Test
    public void correctPostRequestAndSaveToDB() throws Exception {
        final var restTemplate = new TestRestTemplate();
        final var json = new ObjectMapper().writeValueAsString(new LogDTO( "internet-shop"
                                                                         , "controller"
                                                                         , "{\"text\":\"Build Error.404\"}"));
        final var requestBody = new HttpEntity<>(json, getHttpHeaders());
        final var responseEntity = restTemplate.exchange( getURI()
                                                        , HttpMethod.POST
                                                        , requestBody
                                                        , String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1,logRepository.findByTextLog("Build Error.404")
                                           .size());
    }

    @Test
    public void incorrectPostRequestAndSaveToDB() throws Exception {
        final var restTemplate = new TestRestTemplate();
        final var json = new ObjectMapper().writeValueAsString(new LogDTO( "incorrect_project"
                                                                         , "controller"
                                                                         , "{\"text\":\"Build Error\"}"));
        final var requestBody = new HttpEntity<>(json, getHttpHeaders());
        final var responseEntity = restTemplate.exchange( getURI()
                                                        , HttpMethod.POST
                                                        , requestBody
                                                        , String.class);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
        Assert.assertEquals(0,logRepository.findByTextLog("Build Error.404")
                                           .size());
    }

    private String getURI(){
        return new StringBuilder().append("http://localhost:")
                                  .append(port)
                                  .append("/log/save")
                                  .toString();
    }

    private HttpHeaders getHttpHeaders() {
        final var headers = new HttpHeaders();
        final var plainCredentials = "user:user";
        final var base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
