package vrp.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vrp.domain.Module;
import vrp.domain.Project;
import vrp.dto.LogDTO;
import vrp.exception.ResourceNotFoundException;
import vrp.repository.ModuleRepository;
import vrp.service.LogService;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LogServiceImplTest {

    @Autowired
    private LogService logService;

    @Autowired
    private ModuleRepository moduleRepository;

    @Before
    public void before() {
        moduleRepository.save(createTestModule(createTestProject()));
    }

    @Test
    public void saveCorrectLog() {
        final LogDTO logDTO = new LogDTO("new_project","new_module","{\"text\":\"Build Error\"}");
        logService.saveLog(logDTO);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveIncorrectLogWithoutProject() {
        final LogDTO logDTO = new LogDTO("new_project1","new_module","{\"text\":\"Build Error\"}");
        logService.saveLog(logDTO);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveIncorrectLogWithoutModule() {
        final LogDTO logDTO = new LogDTO("new_project","new_module1","{\"text\":\"Build Error\"}");
        logService.saveLog(logDTO);
    }

    private Project createTestProject(){
        return new Project("new_project");
    }

    private Module createTestModule(Project project){
        return new Module("new_module",project);
    }

}