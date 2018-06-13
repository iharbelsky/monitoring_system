package vrp.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.pcollections.HashTreePSet;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vrp.MonitoringApplication;
import vrp.domain.Project;
import vrp.exception.InvalidRequestParams;
import vrp.exception.ResourceExistsException;
import vrp.repository.ModuleRepository;
import vrp.repository.ProjectRepository;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MonitoringApplication.class)
@SpringBootTest
public class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Spy
    private ProjectRepository projectRepository;

    @Spy
    private ModuleRepository moduleRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveProjectAndDependentModulesTest(){
        final var projectName = "internet-shop_new";
        final var moduleName = "controller\nsecurity\nservice\ncontroller";
        final var project = new Project(projectName);
        when((projectRepository).findByNameProject(projectName)).thenReturn(Optional.empty());
        when((projectRepository).save(project)).thenReturn(project);
        when((moduleRepository).save(notNull())).thenReturn(notNull());
        projectService.saveProjectAndDependentModules(projectName, moduleName);
        verify(projectRepository, times(1)).findByNameProject(projectName);
        verify(projectRepository, times(1)).save(notNull());
        verify(moduleRepository, times(3)).save(notNull());
    }

    @Test(expected = ResourceExistsException.class)
    public void saveExistProjectAndDependentModulesTest(){
        final var projectName = "internet-shop_new";
        final var moduleName = "controller\nsecurity\nservice\ncontroller";
        final var project = new Project(projectName);
        when((projectRepository).findByNameProject(projectName)).thenReturn(Optional.of(project));
        projectService.saveProjectAndDependentModules(projectName, moduleName);
        verify(projectRepository, times(1)).findByNameProject(projectName);
    }

    @Test
    public void fetchSetModulesByStringTest(){
        final var str = "controller\n    security   \nservice\n   controller";
        final var expectedSet = HashTreePSet.singleton("controller")
                                            .plus("service")
                                            .plus("security");
        assertEquals(expectedSet, projectService.fetchSetModulesByString(str));
    }

    @Test(expected = InvalidRequestParams.class)
    public void validateRequestParamsTest(){
        final var projectName = "";
        final var moduleName = "controller\nsecurity\nservice\ncontroller";
        projectService.validateRequestParams(projectName,moduleName);
    }
}
