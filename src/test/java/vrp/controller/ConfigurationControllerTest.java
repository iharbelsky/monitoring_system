package vrp.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConfigurationController.class)
public class ConfigurationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConfigurationController configurationController;

    @WithMockUser(roles = "USER")
    @Test
    public void validateMainMenuControllerGet() throws Exception{
        mvc.perform(get("/main").with(SecurityMockMvcRequestPostProcessors.csrf())
                                .accept(MediaType.TEXT_HTML)
                                .contentType(MediaType.TEXT_HTML)
                                .characterEncoding("UTF-8"))
                                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void validateCreateNewProjectGetController() throws Exception{
        mvc.perform(get("/main/create_new_project").with(SecurityMockMvcRequestPostProcessors.csrf())
                                                   .accept(MediaType.TEXT_HTML)
                                                   .contentType(MediaType.TEXT_HTML)
                                                   .characterEncoding("UTF-8"))
                                                   .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Test
    public void validateCreateNewProjectPostController() throws Exception{
        mvc.perform(post("/main/create_new_project").with(SecurityMockMvcRequestPostProcessors.csrf())
                                                    .accept(MediaType.TEXT_HTML)
                                                    .contentType(MediaType.TEXT_HTML)
                                                    .param("name_project", "internet-shop")
                                                    .param("name_modules", "controller" ))
                                                    .andExpect(status().isOk());
    }
}
