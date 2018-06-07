
package vrp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import vrp.dto.LogDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(LogController.class)
public class LogControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LogController logController;


    @WithMockUser(roles = "USER")
    @Test
    public void workPostControllerTest() throws Exception {
        final var model = new ObjectMapper().writeValueAsString(new LogDTO("internet-shop1"
                                                                         , "controller"
                                                                         , "{\"text\":\"Build Success111\"}"));
        mvc.perform(post("/log/write").with(SecurityMockMvcRequestPostProcessors.csrf())
                                      .accept(MediaType.APPLICATION_JSON)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .characterEncoding("UTF-8")
                                      .content(model))
                                      .andExpect(status().isOk());
    }

}

