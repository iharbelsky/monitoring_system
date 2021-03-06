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
import vrp.dto.ModuleEventLogDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ModuleEventLogController.class)
public class ModuleEventLogControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ModuleEventLogController moduleEventLogController;

    @WithMockUser(roles = "USER")
    @Test
    public void validateLogControllerPost() throws Exception {
        final var model = new ObjectMapper().writeValueAsString(new ModuleEventLogDTO("internet-shop"
                                                                                     , "controller"
                                                                                     , "{\"text\":\"Build Success\"}"));
        mvc.perform(post("/module_event/save").with(SecurityMockMvcRequestPostProcessors.csrf())
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .characterEncoding("UTF-8")
                                              .content(model))
                                              .andExpect(status().isOk());
    }
}

