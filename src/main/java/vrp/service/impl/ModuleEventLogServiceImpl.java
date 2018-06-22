package vrp.service.impl;

import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vrp.domain.ModuleEventLog;
import vrp.domain.Module;
import vrp.domain.Project;
import vrp.dto.ModuleEventLogDTO;
import vrp.exception.ResourceNotFoundException;
import vrp.repository.ModuleEventLogRepository;
import vrp.repository.ModuleRepository;
import vrp.repository.ProjectRepository;
import vrp.service.ModuleEventLogService;
import java.util.Date;

@Service
public class ModuleEventLogServiceImpl implements ModuleEventLogService {

    private final ModuleEventLogRepository moduleEventLogRepository;
    private final ProjectRepository projectRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleEventLogServiceImpl( final ModuleEventLogRepository moduleEventLogRepository
                                    , final ProjectRepository projectRepository
                                    , final ModuleRepository moduleRepository) {
        this.moduleEventLogRepository = moduleEventLogRepository;
        this.projectRepository = projectRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    public void saveLog(final ModuleEventLogDTO moduleEventLogDTO) {
        final var module = safetyFetchModule(moduleEventLogDTO);
        final var log = new ModuleEventLog( moduleEventLogDTO.getTextLog()
                                          , new Date()
                                          , module);
        moduleEventLogRepository.save(log);
    }

    protected Module safetyFetchModule(final ModuleEventLogDTO moduleEventLogDTO) {
        final var project = safetyFetchProject(moduleEventLogDTO);
        return safetyFetchModulesByProject(project).stream()
                                                   .filter(obj -> moduleEventLogDTO.getModuleName()
                                                                                   .equals(obj.getModuleName()))
                                                   .findFirst()
                                                   .orElseThrow(() -> new ResourceNotFoundException("Module not found"));
    }

    protected Project safetyFetchProject(final ModuleEventLogDTO moduleEventLogDTO){
        return projectRepository.findByProjectName(moduleEventLogDTO.getProjectName())
                                .orElseThrow(() -> new ResourceNotFoundException("Project not Found"));
    }

    protected PVector<Module> safetyFetchModulesByProject(final Project project){
        return TreePVector.from(moduleRepository.findByProjectId(project.getId()));
    }
}
