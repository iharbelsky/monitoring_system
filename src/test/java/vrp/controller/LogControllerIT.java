package vrp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import vrp.MonitoringApplication;
import vrp.dto.LogDTO;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitoringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class LogControllerIT {

    @LocalServerPort
    private int port;

    @Test
    public void correctPostRequestAndSaveToDB() throws Exception {
        final var restTemplate = new TestRestTemplate();
        final var json = new ObjectMapper().writeValueAsString(new LogDTO( "internet-shop"
                                                                         , "controller"
                                                                         , "{\"text\":\"Build Error\"}"));
        final var requestBody = new HttpEntity<>(json, getHttpHeaders());
        final var responseEntity = restTemplate.exchange( getURI()
                                                        , HttpMethod.POST
                                                        , requestBody
                                                        , String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
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
