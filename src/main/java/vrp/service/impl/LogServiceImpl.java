package vrp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vrp.domain.Log;
import vrp.domain.Module;
import vrp.domain.Project;
import vrp.dto.LogDTO;
import vrp.exception.ResourceNotFoundException;
import vrp.repository.LogRepository;
import vrp.repository.ModuleRepository;
import vrp.repository.ProjectRepository;
import vrp.service.LogService;
import java.util.Date;



@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ProjectRepository projectRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public LogServiceImpl( LogRepository logRepository
                          ,ProjectRepository projectRepository
                          ,ModuleRepository moduleRepository) {
        this.logRepository = logRepository;
        this.projectRepository = projectRepository;
        this.moduleRepository = moduleRepository;
    }


    @Override
    public void writeLog(LogDTO logDTO) {

        final var module = validateModuleName(logDTO);
        final var log = new Log(logDTO.getTextLog()
                               ,new Date()
                               ,module);
        logRepository.save(log);

    }


    protected Module validateModuleName(LogDTO logDTO) {
        final var project = validateProjectName(logDTO);
        final var modules = moduleRepository.findByProjectId(project.getId());
        final var module = modules.stream()
                                  .filter(obj -> logDTO.getModuleName()
                                                       .equals(obj.getNameModule()))
                                  .findFirst()
                                  .orElseThrow(() -> new ResourceNotFoundException("Module not found"));
        return module;
    }

    protected Project validateProjectName(LogDTO logDTO){
        return projectRepository.findByNameProject(logDTO.getProjectName())
                                .orElseThrow(() -> new ResourceNotFoundException("Project not Found"));

    }



}
