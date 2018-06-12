package vrp.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vrp.MonitoringApplication;
import vrp.domain.ModuleEventLog;
import vrp.domain.Module;
import vrp.domain.Project;
import vrp.dto.ModuleEventLogDTO;
import vrp.repository.ModuleEventLogRepository;
import vrp.repository.ModuleRepository;
import vrp.repository.ProjectRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MonitoringApplication.class })
@SpringBootTest
public class ModuleEventLogServiceImplTest {

    @InjectMocks
    private ModuleEventLogServiceImpl moduleEventLogService;

    @Spy
    private ModuleEventLogRepository moduleEventLogRepository;

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
        final var log = new ModuleEventLog( "{\"text\":\"Build Error\"}"
                               , new Date()
                               , module);
        final var logDTO = new ModuleEventLogDTO( "internet-shop"
                                     , "controller"
                                     , "{\"text\":\"Build Error\"}");
        when((projectRepository).findByNameProject(logDTO.getProjectName())).thenReturn(Optional.of(project));
        when((moduleRepository).findByProjectId(project.getId())).thenReturn(List.of(module));
        when((moduleEventLogRepository).save(log)).thenReturn(log);
        moduleEventLogService.saveLog(logDTO);
        verify(moduleEventLogRepository, times(1)).save(notNull());
    }
}