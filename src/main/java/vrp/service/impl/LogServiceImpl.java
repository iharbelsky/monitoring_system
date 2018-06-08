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
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ProjectRepository projectRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public LogServiceImpl( final LogRepository logRepository
                         , final ProjectRepository projectRepository
                         , final ModuleRepository moduleRepository) {
        this.logRepository = logRepository;
        this.projectRepository = projectRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    public void saveLog(final LogDTO logDTO) {
        final var module = safetyFetchModule(logDTO);
        final var log = new Log( logDTO.getTextLog()
                               , new Date()
                               , module);
        logRepository.save(log);
    }

    private Module safetyFetchModule(final LogDTO logDTO) {


        final var project = safetyFetchProject(logDTO);
        return safetyFetchModulesByProject(project).stream()
                                              .filter(obj -> logDTO.getModuleName()
                                                                   .equals(obj.getNameModule()))
                                              .findFirst()
                                              .orElseThrow(() -> new ResourceNotFoundException("Module not found"));
    }

    private Project safetyFetchProject(final LogDTO logDTO){
        return projectRepository.findByNameProject(logDTO.getProjectName())
                                .orElseThrow(() -> new ResourceNotFoundException("Project not Found"));
    }

    private List<Module> safetyFetchModulesByProject(final Project project){
        return moduleRepository.findByProjectId(project.getId());
    }
}
