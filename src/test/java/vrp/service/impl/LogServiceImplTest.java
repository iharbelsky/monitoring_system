package vrp.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import vrp.domain.Log;
import vrp.domain.Module;
import vrp.dto.LogDTO;
import vrp.repository.LogRepository;
import vrp.repository.ModuleRepository;
import vrp.repository.ProjectRepository;
import vrp.service.LogService;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceImplTest {


    @Mock
    private LogService logService;


    @Test
    public void writeLog() {
        final LogDTO logDTO = new LogDTO("internet-shop","controller","{\\\"text\\\":\\\"Build Succes111s\\\"}");
        logService.writeLog(logDTO);



    }
}