package vrp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vrp.dto.LogDTO;

import javax.transaction.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LogControllerIT {

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void validatePostRequestAndSaveToDB() throws Exception{

      final var logDTO = new LogDTO("new_module", "new_module", "{\"text\":\"111Build Error\"}");

       final var model = new ObjectMapper().writeValueAsString(logDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestBody = new HttpEntity<>(model, headers);

        System.out.println(requestBody.getHeaders());
        System.out.println(requestBody.getBody());

        ResponseEntity<String> responseEntity = restTemplate.exchange( "http://localhost:8080/log/save"
                              , HttpMethod.POST
                              , requestBody
                              , String.class);


        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getStatusCodeValue());


    }




}
