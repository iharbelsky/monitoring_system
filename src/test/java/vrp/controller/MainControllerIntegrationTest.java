package vrp.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import vrp.MonitoringApplication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = MonitoringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql( value={"classpath:/sql/delete_all_data.sql","classpath:/sql/insert_test_data.sql"}
    , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MainControllerIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mvc;

    private final String SQL_QUERY = new StringBuilder().append("SELECT m.name_module FROM monitoring.modules m ")
                                                        .append("INNER JOIN monitoring.projects p ON m.id_project = p.id AND p.name_project = 'new_project'")
                                                        .toString();

    @Test
    @WithMockUser(roles = "USER")
    public void correctPostRequestAndSaveToDB() throws Exception{
        mvc.perform(post("/main/create_new_project").with(SecurityMockMvcRequestPostProcessors.csrf())
                                                    .accept(MediaType.TEXT_HTML)
                                                    .contentType(MediaType.TEXT_HTML)
                                                    .param("name_project", "new_project")
                                                    .param("name_modules", "new_module1\nnew_module2\n new_module3"))
                                                    .andExpect(status().isOk());
        final var modules = jdbcTemplate.query(SQL_QUERY, (rs,Long)-> new String(rs.getString("name_module")));
        Assert.assertEquals(3, modules.size());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void incorrectPostRequestAndSaveToDB() throws Exception{
        mvc.perform(post("/main/create_new_project").with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.TEXT_HTML)
                .param("name_project", "internet-shop")
                .param("name_modules", "new_module1\nnew_module2\n new_module3"))
                .andExpect(status().isOk());
        final var modules = jdbcTemplate.query(SQL_QUERY, (rs,Long)-> new String(rs.getString("name_module")));
        Assert.assertEquals(0, modules.size());
    }
}
