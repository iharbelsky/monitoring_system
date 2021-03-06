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
import vrp.exception.InvalidRequestParamsException;
import vrp.exception.ResourceExistsException;
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


    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveProjectAndDependentModulesTest(){
        final var projectName = "internet-shop_new";
        final var description = "This is new internet shop";
        final var moduleNames = new String []{"controller","service","security"};
        final var project = new Project(projectName);
        when((projectRepository).findByProjectName(projectName)).thenReturn(Optional.empty());
        when((projectRepository).save(project)).thenReturn(project);
        projectService.saveProject(projectName, description, moduleNames);
        verify(projectRepository, times(1)).findByProjectName(projectName);
        verify(projectRepository, times(1)).save(notNull());
    }

    @Test(expected = ResourceExistsException.class)
    public void saveExistProjectAndDependentModulesTest(){
        final var projectName = "internet-shop_new";
        final var description = "This is new internet shop";
        final var moduleNames = new String []{"controller","   service","  security"};
        final var project = new Project(projectName);
        when((projectRepository).findByProjectName(projectName)).thenReturn(Optional.of(project));
        projectService.saveProject(projectName, description, moduleNames);
        verify(projectRepository, times(1)).findByProjectName(projectName);
    }
    @Test
    public void fetchSetModulesByStringTest(){
        final var moduleNames = new String []{"controller","   service","  security"};
        final var expectedSet = HashTreePSet.singleton("controller")
                                            .plus("service")
                                            .plus("security");
        assertEquals(expectedSet, projectService.fetchSetModuleNamesByArray(moduleNames));
    }

    @Test(expected = InvalidRequestParamsException.class)
    public void validateRequestParamsTest(){
        final var projectName = "";
        final var moduleNames = new String []{"controller","service","security"};
        projectService.validateRequestParams(projectName, moduleNames);
    }
}
