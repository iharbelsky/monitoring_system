package vrp.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vrp.MonitoringApplication;
import vrp.domain.Log;
import vrp.domain.Module;
import vrp.domain.Project;
import vrp.dto.LogDTO;
import vrp.repository.LogRepository;
import vrp.repository.ModuleRepository;
import vrp.repository.ProjectRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MonitoringApplication.class })
@SpringBootTest
public class LogServiceImplTest {

    @InjectMocks
    private LogServiceImpl logService;

    @Spy
    private LogRepository logRepository;

    @Spy
    private ModuleRepository moduleRepository;

    @Spy
    private ProjectRepository projectRepository;

    @Before
    public void before() {
       MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveCorrectLog() {

        final var project = new Project("internet-shop");
        final var module = new Module("controller", project);
        final var log = new Log( "{\"text\":\"Build Error\"}"
                               , new Date()
                               , module);
        final var logDTO = new LogDTO( "internet-shop"
                                     , "controller"
                                     , "{\"text\":\"Build Error\"}");
        when((projectRepository).findByNameProject(logDTO.getProjectName())).thenReturn(Optional.of(project));
        when((moduleRepository).findByProjectId(project.getId())).thenReturn(List.of(module));
        when((logRepository).save(log)).thenReturn(log);
        logService.saveLog(logDTO);
        verify(logRepository, times(1)).save(notNull());
    }
}